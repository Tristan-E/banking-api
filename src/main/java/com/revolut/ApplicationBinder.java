package com.revolut;

import com.revolut.persistence.repository.AccountRepository;
import com.revolut.persistence.repository.TransactionRepository;
import com.revolut.service.TransactionService;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class ApplicationBinder extends AbstractBinder {
    protected void configure() {
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();
        TransactionService transactionService = new TransactionService(transactionRepository, accountRepository);

        bind(transactionRepository).to(TransactionRepository.class).in(Singleton.class);
        bind(accountRepository).to(AccountRepository.class).in(Singleton.class);
        bind(transactionService).to(TransactionService.class).in(Singleton.class);
    }
}
