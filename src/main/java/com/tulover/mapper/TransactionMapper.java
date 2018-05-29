package com.tulover.mapper;

import com.tulover.dto.TransactionDTO;
import com.tulover.persistence.model.Transaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author teyma
 * @since 28/05/18
 */
@Mapper(uses = MovementMapper.class)
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mappings({
            @Mapping(target = "sourceAccountId", source = "sourceAccount.id"),
            @Mapping(target = "destinationAccountId", source = "destinationAccount.id")
    })
    TransactionDTO transactionToTransactionDTO(Transaction transaction);

    List<TransactionDTO> transactionsToTransactionDTOs(List<Transaction> transactions);

    @InheritInverseConfiguration
    Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);
}
