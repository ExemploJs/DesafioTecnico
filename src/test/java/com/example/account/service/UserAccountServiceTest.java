package com.example.account.service;

import com.example.account.entity.Account;
import com.example.account.request.AccountCreationRequest;
import com.example.account.response.AccountResponse;
import com.example.exception.UserNotFoundException;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    @InjectMocks
    private UserAccountService userAccountService;

    @Mock
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    private Long userId;

    private AccountCreationRequest accountRequest;

    private Account account;

    private AccountResponse actualAccountResponse;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void inactivate() {
        givenAnUserId();
        whenFoundAccountByUserId();
        whenInactivate();
        thenActiveShouldBeFalse();
    }

    @Test
    public void getAccount() {
        givenAnUserId();
        whenFoundAccount();
        thenShouldHaveConvertedCorrectlyIntoAccountResponse();
    }

    @Test
    public void create() {
        givenAnUserId();
        givenAnAccountCreationRequest();
        whenFoundUser();
        whenCreateAccount();
        thenShouldHaveCreatedCorrectly();
    }

    @Test
    public void createErrCauseCouldNotFindUser() {
        givenAnUserId();
        givenAnAccountCreationRequest();
        thenShouldHaveExpectError();
        whenCreateAccount();
    }

    private void givenAnUserId() {
        this.userId = 1L;
    }

    private void givenAnAccountCreationRequest() {
        this.accountRequest = new AccountCreationRequest();
        this.accountRequest.setAccountNumber("123145");
        this.accountRequest.setAgency("1414");
        this.accountRequest.setBalance(new BigDecimal("15000"));
        this.accountRequest.setAgencyDigit("1");
        this.accountRequest.setAccountDigit("0");
    }

    public void whenFoundUser() {
        when(this.userRepository.findById(this.userId))
                .thenReturn(Optional.of(new User("creation1")));
    }

    private void whenCreateAccount() {
        this.userAccountService.create(this.userId, this.accountRequest);
    }

    private void whenFoundAccount() {
        whenFoundAccountByUserId();
        this.actualAccountResponse = this.userAccountService.getAccount(this.userId);
    }

    private void whenFoundAccountByUserId() {
        this.account = new Account();
        this.account.setAccountNumber("1111111");
        this.account.setAgency("1111");
        this.account.setBalance(new BigDecimal("6000"));
        this.account.setAgencyDigit("1");
        this.account.setAccountDigit("0");
        this.account.setActive(true);

        when(this.accountService.findByUserId(this.userId)).thenReturn(this.account);
    }

    private void whenInactivate() {
        this.userAccountService.inactivate(this.userId);
    }

    private void thenShouldHaveCreatedCorrectly() {
        verify(this.accountService, times(1))
                .save(this.accountCaptor.capture());

        final Account actual = this.accountCaptor.getValue();

        assertEquals("123145", actual.getAccountNumber());
        assertEquals("1414", actual.getAgency());
        assertEquals("1", actual.getAgencyDigit());
        assertEquals("0", actual.getAccountDigit());
        assertEquals(new BigDecimal("15000"), actual.getBalance());
    }

    private void thenShouldHaveExpectError() {
        this.expectedException.expect(UserNotFoundException.class);
        this.expectedException.expectMessage("Usuário não encontrado/cadastrado!");
    }

    private void thenShouldHaveConvertedCorrectlyIntoAccountResponse() {
        assertEquals("1111111", this.actualAccountResponse.getAccountNumber());
        assertEquals("1111", this.actualAccountResponse.getAgency());
        assertEquals(new BigDecimal("6000"), this.actualAccountResponse.getBalance());
        assertEquals("1", this.actualAccountResponse.getAgencyDigit());
        assertEquals("0", this.actualAccountResponse.getAccountDigit());
    }

    private void thenActiveShouldBeFalse() {
        assertFalse(this.account.isActive());
    }
}