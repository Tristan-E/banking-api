package com.tulover.service;

import com.google.common.collect.Lists;
import com.tulover.dto.TransactionDTO;
import com.tulover.mapper.TransactionMapper;
import com.tulover.persistence.model.Account;
import com.tulover.persistence.model.Movement;
import com.tulover.persistence.model.Transaction;
import com.tulover.persistence.model.TransactionStatus;
import com.tulover.persistence.repository.AccountRepository;
import com.tulover.persistence.repository.TransactionRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author teyma
 * @since 23/05/18
 */
public class TransactionService {

    private final EntityManager entityManager;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    @Inject
    public TransactionService(final EntityManager entityManager, final TransactionRepository transactionRepository,
                              final TransactionMapper transactionMapper, final AccountRepository accountRepository) {
        this.entityManager = entityManager;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
    }

    public List<TransactionDTO> getTransactions() {
        return transactionMapper.transactionsToTransactionDTOs(
                transactionRepository.findAll()
        );
    }

    public TransactionDTO getTransaction(long id) {
        return transactionMapper.transactionToTransactionDTO(
                transactionRepository.findOne(id)
        );
    }

    public void create(TransactionDTO transactionDTO) {;
        Transaction transaction = transactionMapper.transactionDTOToTransaction(transactionDTO);

        entityManager.getTransaction().begin();

        try {
            Account sourceAccount = accountRepository.findOne(transaction.getSourceAccount().getId());
            Account destinationAccount = accountRepository.findOne(transaction.getDestinationAccount().getId());

            transaction.setSourceAccount(sourceAccount);
            transaction.setDestinationAccount(destinationAccount);

            BigDecimal sourceAccountBalance = sourceAccount.getMovements().stream()
                    .map(m -> m.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Verifying balance allows the transaction
            if (sourceAccountBalance.subtract(transaction.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
                transaction.setStatus(TransactionStatus.CHALLENGED);
            } else {
                doTransaction(transaction);
            }
            entityManager.persist(transaction);

            entityManager.getTransaction().commit();
        } catch (PersistenceException p) {
            entityManager.getTransaction().rollback();
        }
    }

    public void authorizeTransaction(long transactionId) {
        entityManager.getTransaction().begin();

        try {
            Transaction transaction = transactionRepository.findOne(transactionId);

            if (transaction == null || !TransactionStatus.CHALLENGED.equals(transaction.getStatus())) {
                entityManager.getTransaction().rollback();
                throw new Error("TODO CREATE ERROR");
            }

            doTransaction(transaction);
            entityManager.merge(transaction);

            entityManager.getTransaction().commit();
        } catch (PersistenceException p) {
            entityManager.getTransaction().rollback();
        }
    }

    public void denyTransaction(long transactionId) {
        entityManager.getTransaction().begin();

        try{
            Transaction transaction = transactionRepository.findOne(transactionId);

            if(transaction == null || !TransactionStatus.CHALLENGED.equals(transaction.getStatus())) {
                entityManager.getTransaction().rollback();
                throw new Error("TODO CREATE ERROR");
            }

            transaction.setStatus(TransactionStatus.DENIED);
            entityManager.merge(transaction);

            entityManager.getTransaction().commit();
        } catch (PersistenceException p) {
            entityManager.getTransaction().rollback();
        }
    }

    /**
     * Create movement on each account and associate entities.
     * @param transaction
     */
    private void doTransaction(Transaction transaction) {
        Account sourceAccount = transaction.getSourceAccount();
        Account destinationAccount = transaction.getDestinationAccount();

        Movement sourceMovement = new Movement(sourceAccount, transaction.getAmount().negate());
        Movement destinationMovement = new Movement(destinationAccount, transaction.getAmount());

        sourceAccount.getMovements().add(sourceMovement);
        destinationAccount.getMovements().add(destinationMovement);

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setMovements(Lists.newArrayList(sourceMovement, destinationMovement));

        entityManager.persist(sourceMovement);
        entityManager.persist(destinationMovement);
        entityManager.merge(sourceAccount);
        entityManager.merge(destinationAccount);
    }
}
