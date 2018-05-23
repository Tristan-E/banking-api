package com.revolut.persistence.repository;

import com.revolut.persistence.AbstractHibernateRepository;
import com.revolut.persistence.model.Account;

/**
 * @author teyma
 * @since 21/05/2018
 */
public class AccountRepository extends AbstractHibernateRepository<Account> {
    public AccountRepository(){
        super(Account.class);
    }
}
