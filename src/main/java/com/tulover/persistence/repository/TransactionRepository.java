package com.tulover.persistence.repository;

import com.tulover.persistence.model.Transaction;
import com.tulover.persistence.AbstractHibernateRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class TransactionRepository extends AbstractHibernateRepository<Transaction> {
    @Inject
    private final EntityManager entityManager;

    public TransactionRepository(final EntityManager entityManager){
        super(entityManager, Transaction.class);
        this.entityManager = entityManager;
    }
}
