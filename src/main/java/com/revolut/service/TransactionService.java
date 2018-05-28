package com.revolut.service;

import com.revolut.persistence.PersistenceUtil;
import com.revolut.persistence.model.Account;
import com.revolut.persistence.model.Movement;
import com.revolut.persistence.model.Transaction;
import com.revolut.persistence.model.TransactionStatus;
import com.revolut.persistence.repository.AccountRepository;
import com.revolut.persistence.repository.TransactionRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;

/**
 * @author teyma
 * @since 23/05/18
 */
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Inject
    public TransactionService(final TransactionRepository transactionRepository, final AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public void create(Transaction transaction) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();
        entityManager.getTransaction().begin();

        Account sourceAccount = accountRepository.findOne(transaction.getSourceAccount().getId());
        Account destinationAccount = accountRepository.findOne(transaction.getDestinationAccount().getId());

        BigDecimal sourceAccountBalance = sourceAccount.getMovements().stream()
                .map(m -> m.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Verifying balance allows the transaction
        if(sourceAccountBalance.subtract(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            transaction.setStatus(TransactionStatus.CHALLENGED);
        } else {
            Movement sourceMovement = new Movement(sourceAccount, transaction.getAmount().negate());
            Movement destinationMovement = new Movement(destinationAccount, transaction.getAmount());
            
            sourceAccount.getMovements().add(sourceMovement);
            destinationAccount.getMovements().add(destinationMovement);

            transaction.setStatus(TransactionStatus.COMPLETED);

            entityManager.persist(sourceMovement);
            entityManager.persist(destinationMovement);
            entityManager.merge(sourceAccount);
            entityManager.merge(destinationAccount);
        }
        entityManager.persist(transaction);

        entityManager.getTransaction().commit();
    }
}
