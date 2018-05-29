package com.tulover.persistence.repository;

import com.tulover.persistence.model.Transaction;
import com.tulover.persistence.AbstractHibernateRepository;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class TransactionRepository extends AbstractHibernateRepository<Transaction> {
    public TransactionRepository(){
        super(Transaction.class);
    }
}
