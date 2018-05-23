package com.revolut.service;

import com.revolut.persistence.PersistenceUtil;
import com.revolut.persistence.model.Account;
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

        // Verifying balance allows the transaction
        if(sourceAccount.getCurrentBalance().subtract(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            transaction.setStatus(TransactionStatus.CHALLENGED);
        } else {
            sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance().subtract(transaction.getAmount()));
            destinationAccount.setCurrentBalance(destinationAccount.getCurrentBalance().add(transaction.getAmount()));
            transaction.setStatus(TransactionStatus.COMPLETED);

            entityManager.persist(sourceAccount);
            entityManager.persist(destinationAccount);
        }

        entityManager.persist(transaction);

        entityManager.getTransaction().commit();
    }
}
