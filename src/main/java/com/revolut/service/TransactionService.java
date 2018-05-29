package com.revolut.service;

import com.google.common.collect.Lists;
import com.revolut.persistence.PersistenceUtil;
import com.revolut.persistence.model.Account;
import com.revolut.persistence.model.Movement;
import com.revolut.persistence.model.Transaction;
import com.revolut.persistence.model.TransactionStatus;
import com.revolut.persistence.repository.AccountRepository;
import com.revolut.persistence.repository.TransactionRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
                doTransaction(entityManager, transaction, sourceAccount, destinationAccount);
            }
            entityManager.persist(transaction);

            entityManager.getTransaction().commit();
        } catch (PersistenceException p) {
            entityManager.getTransaction().rollback();
        }
    }

    public void authorizeTransaction(long transactionId) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();
        entityManager.getTransaction().begin();

        try {
            Transaction transaction = transactionRepository.findOne(transactionId);

            if (transaction == null || !TransactionStatus.CHALLENGED.equals(transaction.getStatus())) {
                entityManager.getTransaction().rollback();
                throw new Error("TODO CREATE ERROR");
            }

            Account sourceAccount = transaction.getSourceAccount();
            Account destinationAccount = transaction.getDestinationAccount();

            doTransaction(entityManager, transaction, sourceAccount, destinationAccount);
            entityManager.merge(transaction);

            entityManager.getTransaction().commit();
        } catch (PersistenceException p) {
            entityManager.getTransaction().rollback();
        }
    }

    public void denyTransaction(long transactionId) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();
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
     * @param entityManager
     * @param transaction
     * @param sourceAccount
     * @param destinationAccount
     */
    private void doTransaction(EntityManager entityManager, Transaction transaction, Account sourceAccount, Account destinationAccount) {
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
