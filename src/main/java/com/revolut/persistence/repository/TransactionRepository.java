package com.revolut.dao;

import com.revolut.model.Transaction;

import java.util.List;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class TransactionRepository {

    public List<Transaction> findAll() {
        return PersistenceUtil.getEntityManager().createQuery("from Transaction", Transaction.class).getResultList();
    }
}
