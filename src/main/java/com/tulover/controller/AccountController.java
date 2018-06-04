package com.tulover.controller;

import com.tulover.dto.AccountDTO;
import com.tulover.service.AccountService;
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
@Api(value = "Account Controller")
public class AccountController {

    private final AccountService accountService;

    @Inject
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDTO getAccount(@PathParam("id") long id) {
        return accountService.getAccount(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createAccount(AccountDTO accountDTO) {
        accountService.create(accountDTO);
    }
}