package com.revolut.controller;

import com.revolut.dto.TransactionDTO;
import com.revolut.mapper.TransactionMapper;
import com.revolut.persistence.model.Transaction;
import com.revolut.persistence.repository.TransactionRepository;
import com.revolut.service.TransactionService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author teyma
 * @since 21/05/18
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionMapper transactionMapper;

    @Test
    public void shouldGetTransactions() {
        final List<TransactionDTO> transactionDTOs = new ArrayList<>();
        final List<Transaction> transactions = new ArrayList<>();

        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);
        Mockito.when(transactionMapper.transactionsToTransactionDTOs(Mockito.anyList())).thenReturn(transactionDTOs);

        final List<TransactionDTO> transactionsReturned = transactionController.getTransactions();

        Mockito.verify(transactionMapper).transactionsToTransactionDTOs(transactions);

        Assert.assertEquals(transactionsReturned, transactions);
    }

    @Test
    public void shouldGetTransaction() {
        final TransactionDTO transactionDTO = new TransactionDTO();
        final Transaction transaction = new Transaction();
        final long transactionId = 42L;

        Mockito.when(transactionRepository.findOne(Mockito.anyLong())).thenReturn(transaction);
        Mockito.when(transactionMapper.transactionToTransactionDTO(Mockito.any())).thenReturn(transactionDTO);

        final TransactionDTO transactionReturned = transactionController.getTransaction(transactionId);

        Mockito.verify(transactionMapper).transactionToTransactionDTO(transaction);
        Mockito.verify(transactionRepository).findOne(transactionId);

        Assert.assertEquals(transactionReturned, transaction);
    }

    @Test
    public void shouldCreateTransaction() {
        final TransactionDTO transactionDTO = new TransactionDTO();
        final Transaction transaction = new Transaction();

        Mockito.when(transactionMapper.transactionDTOToTransaction(Mockito.any())).thenReturn(transaction);

        transactionController.createTransaction(transactionDTO);

        Mockito.verify(transactionMapper).transactionDTOToTransaction(transactionDTO);
        Mockito.verify(transactionService).create(transaction);
    }

    @Test
    public void shouldAuthorizeTransadction() {
        long transactionId = 42L;
        transactionController.authorizeTransaction(transactionId);
        Mockito.verify(transactionService).authorizeTransaction(transactionId);
    }

    @Test
    public void shouldDenyTransaction() {
        long transactionId = 42L;
        transactionController.denyTransaction(transactionId);
        Mockito.verify(transactionService).denyTransaction(transactionId);
    }
}
