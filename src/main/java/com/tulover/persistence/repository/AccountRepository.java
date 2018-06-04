package com.tulover.persistence.repository;

import com.tulover.persistence.AbstractHibernateCRURepository;
import com.tulover.persistence.model.Account;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author teyma
 * @since 21/05/2018
 */
public class AccountRepository extends AbstractHibernateCRURepository<Account> {

    public AccountRepository(final EntityManager entityManager){
        super(entityManager, Account.class);
    }
}
