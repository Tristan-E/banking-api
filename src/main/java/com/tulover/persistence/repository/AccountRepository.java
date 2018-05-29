package com.tulover.persistence.repository;

import com.tulover.persistence.AbstractHibernateRepository;
import com.tulover.persistence.model.Account;

/**
 * @author teyma
 * @since 21/05/2018
 */
public class AccountRepository extends AbstractHibernateRepository<Account> {
    public AccountRepository(){
        super(Account.class);
    }
}
