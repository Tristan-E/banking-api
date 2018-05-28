package com.revolut.mapper;

import com.google.common.collect.Lists;
import com.revolut.dto.AccountDTO;
import com.revolut.dto.MovementDTO;
import com.revolut.persistence.model.Account;
import com.revolut.persistence.model.Movement;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author teyma
 * @since 28/05/18
 */
public class AccountMapperTest {
    @Test
    public void shouldMapAccountToAccountDTO() {
        Movement movement1 = new Movement();
        movement1.setAmount(new BigDecimal(100));
        Movement movement2 = new Movement();
        movement2.setAmount(new BigDecimal(200));

        //given
        Account account = new Account();
        account.setId(33L);
        account.setMovements(Lists.newArrayList(movement1, movement2));

        //when
        AccountDTO accountDTO = AccountMapper.INSTANCE.accountToAccountDTO(account);

        //then
        defaultAssertions(account, accountDTO);

        Assert.assertEquals(new BigDecimal(300), accountDTO.getBalance());
    }

    @Test
    public void shouldMapAccountDTOtoAccount() {
        //given
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(678L);
        accountDTO.setMovements(Lists.newArrayList(new MovementDTO(), new MovementDTO()));

        //when
        Account account = AccountMapper.INSTANCE.accountToAccountDTO(accountDTO);

        //then
        defaultAssertions(account, accountDTO);
    }

    private void defaultAssertions(Account account, AccountDTO accountDTO) {
        Assert.assertEquals(account.getId(), accountDTO.getId());
        Assert.assertEquals(account.getMovements().size(), accountDTO.getMovements().size());
    }
}
