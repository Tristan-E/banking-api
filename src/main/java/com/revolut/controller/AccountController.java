package com.revolut.controller;

import com.revolut.persistence.model.Account;
import com.revolut.persistence.repository.AccountRepository;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author teyma
 * @since 21/05/2018
 */
@Path("/accounts")
@Api
public class AccountController {

    private final AccountRepository accountRepository;

    @Inject
    public AccountController(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}