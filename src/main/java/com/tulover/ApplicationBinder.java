package com.tulover;

import com.tulover.mapper.AccountMapper;
import com.tulover.mapper.TransactionMapper;
import com.tulover.persistence.PersistenceUtil;
import com.tulover.persistence.repository.AccountRepository;
import com.tulover.persistence.repository.TransactionRepository;
import com.tulover.service.TransactionService;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class ApplicationBinder extends AbstractBinder {
    protected void configure() {
        // Initializing Persistence
        EntityManager entityManager = PersistenceUtil.getEntityManager();

        TransactionRepository transactionRepository = new TransactionRepository(entityManager);
        AccountRepository accountRepository = new AccountRepository(entityManager);
        TransactionService transactionService = new TransactionService(entityManager, transactionRepository, accountRepository);
        TransactionMapper transactionMapper = TransactionMapper.INSTANCE;
        AccountMapper accountMapper = AccountMapper.INSTANCE;

        bind(transactionRepository).to(TransactionRepository.class).in(Singleton.class);
        bind(accountRepository).to(AccountRepository.class).in(Singleton.class);
        bind(transactionService).to(TransactionService.class).in(Singleton.class);
        bind(transactionMapper).to(TransactionMapper.class).in(Singleton.class);
        bind(accountMapper).to(AccountMapper.class).in(Singleton.class);
    }
}
