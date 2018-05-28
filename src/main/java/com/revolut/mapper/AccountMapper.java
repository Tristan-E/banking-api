package com.revolut.mapper;

import com.revolut.dto.AccountDTO;
import com.revolut.persistence.model.Account;
import com.revolut.persistence.model.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author teyma
 * @since 28/05/18
 */
@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "movements", target= "balance", qualifiedByName = "calculateBalance")
    AccountDTO accountToAccountDTO(Account account);

    List<AccountDTO> accountsToAccountDTOs(List<Account> accounts);

    Account accountDTOToAccount(AccountDTO accountDTO);

    /**
     * Sum each movement amount
     * @param movements
     * @return
     */
    @Named("calculateBalance")
    default BigDecimal calculateBalance(List<Movement> movements) {
        BigDecimal balance = BigDecimal.ZERO;
        if(movements != null) {
            balance = movements.stream().map(
                    m -> m.getAmount() != null ? m.getAmount() : BigDecimal.ZERO
            ).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return balance;
    }
}
