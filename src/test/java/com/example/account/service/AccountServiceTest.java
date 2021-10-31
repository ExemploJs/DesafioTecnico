package com.example.account.service;

import com.example.account.entity.Account;
import com.example.account.repository.AccountRepository;
import com.example.exception.AccountNotFoundException;
import com.example.user.entity.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    
    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Long userId;

    @Test
    public void save() {
        givenAnAccount();
        whenSave();
        thenShouldSaveNewAccount();
    }

    @Test
    public void findByUserId() {
        givenAnUserId();
        whenFindByUserId();
        thenShouldHaveFound();
    }

    @Test
    public void findByUserIdButNothingHasFound() {
        givenAnUserId();
        thenExpectSomeError();
        whenFindByUserIdErr();
    }

    private void givenAnUserId() {
        this.userId = 1L;
    }

    private void givenAnAccount() {
        this.account = new Account();
        this.account.setAccountNumber("892304");
        this.account.setAgency("5454");
        this.account.setAccountDigit("1");
        this.account.setAgencyDigit("5");
        this.account.setActive(true);
        this.account.setBalance(new BigDecimal("5000"));

        final User user = new User();
        user.setUserName("test");
        user.setId(1L);
        this.account.setUser(user);
    }

    private void whenSave() {
        this.accountService.save(this.account);
    }

    private void whenFindByUserId() {
        when(this.accountRepository.findByUserId(this.userId)).thenReturn(new Account());
        this.accountService.findByUserId(this.userId);
    }

    private void whenFindByUserIdErr() {
        this.accountService.findByUserId(this.userId);
    }

    private void thenShouldSaveNewAccount() {
        verify(this.accountRepository, times(1)).save(this.account);
    }

    private void thenShouldHaveFound() {
        verify(this.accountRepository, times(1)).findByUserId(this.userId);
    }

    private void thenExpectSomeError() {
        this.expectedException.expect(AccountNotFoundException.class);
        this.expectedException.expectMessage("Conta não encontrada ou não cadastrada!");
    }
}