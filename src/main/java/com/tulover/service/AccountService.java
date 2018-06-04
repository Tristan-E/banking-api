package com.tulover.service;

import com.tulover.dto.AccountDTO;
import com.tulover.mapper.AccountMapper;
import com.tulover.persistence.model.Account;
import com.tulover.persistence.repository.AccountRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * @author teyma
 * @since 29/05/18
 */
public class AccountService {

    private final EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Inject
    public AccountService(final EntityManager entityManager, final AccountRepository accountRepository, final AccountMapper accountMapper) {
        this.entityManager = entityManager;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public List<AccountDTO> getAccounts() {
        return accountMapper.accountsToAccountDTOs(
                accountRepository.findAll()
        );
    }

    public AccountDTO getAccount(long id) {
        return accountMapper.accountToAccountDTO(
                accountRepository.findOne(id)
        );
    }
    public void create(AccountDTO accountDTO) {
        Account account = accountMapper.accountDTOToAccount(accountDTO);

        entityManager.getTransaction().begin();
        try {
            accountRepository.create(account);
        } catch (PersistenceException p) {
            // TODO
            entityManager.getTransaction().rollback();
        }
    }
}
