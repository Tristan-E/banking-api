package com.revolut.controller;

import com.revolut.persistence.repository.TransactionRepository;
import com.revolut.model.Transaction;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/transactions")
@Api
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @Inject
    public TransactionController(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}