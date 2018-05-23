package com.revolut.controller;

import com.revolut.persistence.repository.TransactionRepository;
import com.revolut.persistence.model.Transaction;
import com.revolut.service.TransactionService;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/transactions")
@Api
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;


    @Inject
    public TransactionController(final TransactionRepository transactionRepository, final TransactionService transactionService) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void createTransaction(Transaction transaction) {
        transactionService.create(transaction);
    }
}