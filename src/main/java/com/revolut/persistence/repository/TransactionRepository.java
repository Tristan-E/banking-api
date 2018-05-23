package com.revolut.persistence.repository;

import com.revolut.persistence.model.Transaction;
import com.revolut.persistence.AbstractHibernateRepository;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class TransactionRepository extends AbstractHibernateRepository<Transaction> {
    public TransactionRepository(){
        super(Transaction.class);
    }
}
