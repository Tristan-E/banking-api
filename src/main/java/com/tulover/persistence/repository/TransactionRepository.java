package com.tulover.persistence.repository;

import com.tulover.persistence.model.Transaction;
import com.tulover.persistence.AbstractHibernateCRURepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class TransactionRepository extends AbstractHibernateCRURepository<Transaction> {

    public TransactionRepository(final EntityManager entityManager){
        super(entityManager, Transaction.class);
    }
}
