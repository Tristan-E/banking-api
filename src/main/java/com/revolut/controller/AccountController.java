package com.revolut.controller;

import com.revolut.dto.AccountDTO;
import com.revolut.mapper.AccountMapper;
import com.revolut.persistence.model.Account;
import com.revolut.persistence.repository.AccountRepository;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author teyma
 * @since 21/05/2018
 */
@Path("/accounts")
@Api
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Inject
    public AccountController(final AccountRepository accountRepository, final AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountDTO> getAccounts() {
        return accountMapper.accountsToAccountDTOs(
                accountRepository.findAll()
        );
    }
}