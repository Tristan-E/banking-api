package com.tulover.mapper;

import com.google.common.collect.Lists;
import com.tulover.dto.MovementDTO;
import com.tulover.dto.TransactionDTO;
import com.tulover.persistence.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author teyma
 * @since 28/05/18
 */
public class TransactionMapperTest {
    @Test
    public void shouldMapTransactionToTransactionDTO() {
        Account sourceAccount = new Account();
        sourceAccount.setId(2L);

        Account destinationAccount = new Account();
        sourceAccount.setId(3L);

        //given
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setStatus(TransactionStatus.CHALLENGED);
        transaction.setAmount(new BigDecimal(666));
        transaction.setDestinationAccount(destinationAccount);
        transaction.setSourceAccount(sourceAccount);
        transaction.setDate(ZonedDateTime.now());
        transaction.setMethod(TransactionMethod.CREDIT_CARD);
        transaction.setMovements(Lists.newArrayList(new Movement(),new Movement(),new Movement()));

        //when
        TransactionDTO transactionDTO = TransactionMapper.INSTANCE.transactionToTransactionDTO(transaction);

        //then
        defaultAssertions(transaction, transactionDTO);
    }

    @Test
    public void shouldMapTransactionDTOtoTransaction() {
        //given
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(99L);
        transactionDTO.setStatus(TransactionStatus.DENIED);
        transactionDTO.setAmount(new BigDecimal(4567));
        transactionDTO.setDestinationAccountId(8L);
        transactionDTO.setSourceAccountId(1L);
        transactionDTO.setDate(ZonedDateTime.now());
        transactionDTO.setMethod(TransactionMethod.BANK_TRANSFER);
        transactionDTO.setMovements(Lists.newArrayList(new MovementDTO(),new MovementDTO(),new MovementDTO()));

        //when
        Transaction transaction = TransactionMapper.INSTANCE.transactionDTOToTransaction(transactionDTO);

        //then
        defaultAssertions(transaction, transactionDTO);
    }

    private void defaultAssertions(Transaction transaction, TransactionDTO transactionDTO) {
        Assert.assertEquals(transaction.getId(), transactionDTO.getId());
        Assert.assertEquals(transaction.getStatus(), transactionDTO.getStatus());
        Assert.assertEquals(transaction.getAmount(), transactionDTO.getAmount());
        Assert.assertEquals(transaction.getDestinationAccount().getId(), transactionDTO.getDestinationAccountId());
        Assert.assertEquals(transaction.getSourceAccount().getId(), transactionDTO.getSourceAccountId());
        Assert.assertEquals(transaction.getDate(), transactionDTO.getDate());
        Assert.assertEquals(transaction.getMethod(), transactionDTO.getMethod());
        Assert.assertEquals(transaction.getMovements().size(), transactionDTO.getMovements().size());
    }
}
