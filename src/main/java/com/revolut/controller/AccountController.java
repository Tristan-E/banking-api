package com.revolut.controller;

import com.revolut.dto.AccountDTO;
import com.revolut.dto.TransactionDTO;
import com.revolut.mapper.AccountMapper;
import com.revolut.persistence.repository.AccountRepository;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.*;
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDTO getAccount(@PathParam("id") long id) {
        return accountMapper.accountToAccountDTO(
                accountRepository.findOne(id)
        );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createAccount(AccountDTO accountDTO) {
        accountRepository.create(
                accountMapper.accountDTOToAccount(accountDTO)
        );
    }
}