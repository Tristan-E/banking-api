package com.revolut.controller;

import com.revolut.dto.TransactionDTO;
import com.revolut.mapper.TransactionMapper;
import com.revolut.persistence.model.TransactionStatus;
import com.revolut.persistence.repository.TransactionRepository;
import com.revolut.persistence.model.Transaction;
import com.revolut.service.TransactionService;
import io.swagger.annotations.Api;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transactions")
@Api
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Inject
    public TransactionController(final TransactionRepository transactionRepository,
                                 final TransactionService transactionService,
                                 final TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TransactionDTO> getTransactions() {
        return transactionMapper.transactionsToTransactionDTOs(
                transactionRepository.findAll()
        );
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionDTO getTransaction(@PathParam("id") long id) {
        return transactionMapper.transactionToTransactionDTO(
                transactionRepository.findOne(id)
        );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createTransaction(TransactionDTO transactionDTO) {
        transactionService.create(
                transactionMapper.transactionDTOToTransaction(transactionDTO)
        );
    }

    @PUT
    @Path("/{id}/authorize")
    public void authorizeTransaction(@PathParam("id") long id) {
        transactionService.authorizeTransaction(id);
    }

    @PUT
    @Path("/{id}/deny")
    public void denyTransaction(@PathParam("id") long id) {
        transactionService.denyTransaction(id);
    }
}