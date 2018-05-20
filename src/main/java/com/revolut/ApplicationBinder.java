package com.revolut;

import com.revolut.persistence.repository.TransactionRepository;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class ApplicationBinder extends AbstractBinder {
    protected void configure() {
        bind(new TransactionRepository()).to(TransactionRepository.class).in(Singleton.class);
    }
}
