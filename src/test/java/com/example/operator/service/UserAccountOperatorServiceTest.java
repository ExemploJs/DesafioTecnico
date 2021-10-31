package com.example.operator.service;

import com.example.account.entity.Account;
import com.example.account.service.AccountService;
import com.example.exception.FieldCannotBeNullException;
import com.example.exception.FromUserIdCannotBeTheSameOfToUserIdException;
import com.example.exception.ValueCannotBeNegativeOrZeroException;
import com.example.history.producer.HistoryProducer;
import com.example.operator.request.BillRequest;
import com.example.operator.request.TransferRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountOperatorServiceTest {

    @InjectMocks
    private UserAccountOperatorService service;

    @Mock
    private AccountService accountService;

    @Mock
    private HistoryProducer historyProducer;

    private long userId;

    private BigDecimal value;

    private Account account;

    private long toUserId;

    private TransferRequest transferRequest;

    private Account toAccount;

    private BillRequest billRequest;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void withdraw() {
        givenAnUserId();
        givenAValue();
        whenFindByUserId();
        whenWithdraw();
        thenShouldHaveDrawnCorrectly();
    }

    @Test
    public void withdrawNotFilledValueErr() {
        givenAnUserId();
        thenShouldExpectErrorCauseTheValueIsNotFilled();
        whenWithdraw();
    }

    @Test
    public void withdrawZeroValueErr() {
        givenAnUserId();
        givenAZeroValue();
        thenShouldExpectErrorCauseTheValueIsNegativeOrZero();
        whenWithdraw();
    }

    @Test
    public void withdrawNegativeValueErr() {
        givenAnUserId();
        givenANegativeValue();
        thenShouldExpectErrorCauseTheValueIsNegativeOrZero();
        whenWithdraw();
    }

    @Test
    public void deposit() {
        givenAnUserId();
        givenAValue();
        whenFindByUserId();
        whenDeposit();
        thenShouldHaveDepositedCorrectly();
    }

    @Test
    public void depositNotFilledValueErr() {
        givenAnUserId();
        thenShouldExpectErrorCauseTheValueIsNotFilled();
        whenDeposit();
    }

    @Test
    public void depositNegativeValueErr() {
        givenAnUserId();
        givenANegativeValue();
        thenShouldExpectErrorCauseTheValueIsNegativeOrZero();
        whenDeposit();
    }

    @Test
    public void depositZeroValueErr() {
        givenAnUserId();
        givenAZeroValue();
        thenShouldExpectErrorCauseTheValueIsNegativeOrZero();
        whenDeposit();
    }

    @Test
    public void transfer() {
        givenAFromUserId();
        givenAToUserId();
        givenATransferRequest();
        whenFindByFromUserId();
        whenFindByToUserId();
        whenTransfer();
        thenShouldHaveTransferredCorrectly();
    }

    @Test
    public void transferNegativeValueErr() {
        givenAFromUserId();
        givenAToUserId();
        givenATransferRequestNegativeValue();
        thenShouldExpectErrorCauseTheValueIsNegativeOrZero();
        whenTransfer();
    }

    @Test
    public void transferZeroValueErr() {
        givenAFromUserId();
        givenAToUserId();
        givenATransferRequestZeroValue();
        thenShouldExpectErrorCauseTheValueIsNegativeOrZero();
        whenTransfer();
    }

    @Test
    public void transferNullValueErr() {
        givenAFromUserId();
        givenAToUserId();
        givenATransferRequestNullValue();
        thenShouldExpectErrorCauseTheValueIsNotFilledTransference();
        whenTransfer();
    }

    @Test
    public void transferNullValueTransferObjectItselfNullErr() {
        givenAFromUserId();
        givenAToUserId();
        thenShouldExpectErrorCauseTheValueIsNotFilledTransference();
        whenTransfer();
    }

    @Test
    public void transferUserIdsAreTheSameErr() {
        givenAFromUserId();
        givenAToUserIdTheSameAsFrom();
        givenATransferRequest();
        thenShouldExpectErrorCauseTheValueOfFromAndToUserIdAreTheSame();
        whenTransfer();
    }

    @Test
    public void payBill() {
        givenAnUserId();
        givenABillRequest();
        whenFindByUserId();
        whenPaidBill();
        thenShouldHavePaidBillsCorrectly();
    }

    @Test
    public void payBillNullFieldsErr() {
        givenAnUserId();
        givenANullInformationBillRequest();
        thenShouldExpectErrorCauseTheBillRequestIsNotFilled();
        whenPaidBill();
    }

    @Test
    public void payBillNullItselfErr() {
        givenAnUserId();
        thenShouldExpectErrorCauseTheBillRequestIsNotFilled();
        whenPaidBill();
    }

    @Test
    public void payBillWhenZeroValueBillErr() {
        givenAnUserId();
        givenAZeroValueInformationBillRequest();
        thenShouldExpectErrorCauseTheValueIsNegativeOrZero();
        whenPaidBill();
    }

    @Test
    public void payBillWhenNegativeValueBillErr() {
        givenAnUserId();
        givenANegativeValueInformationBillRequest();
        thenShouldExpectErrorCauseTheValueIsNegativeOrZero();
        whenPaidBill();
    }

    private void givenAValue() {
        this.value = new BigDecimal("5000");
    }

    private void givenAnUserId() {
        this.userId = 1L;
    }

    private void givenAZeroValue() {
        this.value = BigDecimal.ZERO;
    }

    private void givenANegativeValue() {
        this.value = new BigDecimal("-1");
    }

    private void givenATransferRequest() {
        this.transferRequest = new TransferRequest();
        this.transferRequest.setMessage("transf test");
        this.transferRequest.setTransferedValue(new BigDecimal("2500"));
    }

    private void givenATransferRequestNegativeValue() {
        this.transferRequest = new TransferRequest();
        this.transferRequest.setMessage("transf test");
        this.transferRequest.setTransferedValue(new BigDecimal("-2500"));
    }

    private void givenATransferRequestZeroValue() {
        this.transferRequest = new TransferRequest();
        this.transferRequest.setMessage("transf test");
        this.transferRequest.setTransferedValue(new BigDecimal("-2500"));
    }

    private void givenATransferRequestNullValue() {
        this.transferRequest = new TransferRequest();
        this.transferRequest.setMessage("transf test");
    }

    private void givenAToUserId() {
        this.toUserId = 2L;
    }

    private void givenAToUserIdTheSameAsFrom() {
        this.toUserId = 1L;
    }

    private void givenAFromUserId() {
        givenAnUserId();
    }

    private void givenAZeroValueInformationBillRequest() {
        this.billRequest = new BillRequest();
        this.billRequest.setValue(BigDecimal.ZERO);
        this.billRequest.setBarCode("12124141");
        this.billRequest.setDescription("test");
    }

    private void givenANegativeValueInformationBillRequest() {
        this.billRequest = new BillRequest();
        this.billRequest.setValue(BigDecimal.ZERO);
        this.billRequest.setBarCode("12124141");
        this.billRequest.setDescription("test");
    }

    private void givenANullInformationBillRequest() {
        this.billRequest = new BillRequest();
        this.billRequest.setBarCode(null);
        this.billRequest.setValue(new BigDecimal("5.00"));
    }

    private void givenABillRequest() {
        this.billRequest = new BillRequest();
        this.billRequest.setBarCode("12414141");
        this.billRequest.setDescription("test bill");
        this.billRequest.setValue(new BigDecimal("222"));
    }

    private void whenDeposit() {
        this.service.deposit(this.userId, this.value);
    }

    private void whenWithdraw() {
        this.service.withdraw(this.userId, this.value);
    }

    private void whenFindByUserId() {
        this.account = new Account();
        this.account.setBalance(new BigDecimal("10000"));
        when(this.accountService.findByUserId(this.userId)).thenReturn(this.account);
    }

    private void whenTransfer() {
        this.service.transfer(this.userId, this.toUserId, this.transferRequest);
    }

    private void whenFindByFromUserId() {
        whenFindByUserId();
    }

    private void whenFindByToUserId() {
        this.toAccount = new Account();
        this.toAccount.setBalance(new BigDecimal("15000"));
        when(this.accountService.findByUserId(this.toUserId)).thenReturn(this.toAccount);
    }

    private void whenPaidBill() {
        this.service.payBill(this.userId, this.billRequest);
    }

    private void thenShouldExpectErrorCauseTheValueOfFromAndToUserIdAreTheSame() {
        this.expectedException.expect(FromUserIdCannotBeTheSameOfToUserIdException.class);
        this.expectedException.expectMessage("O id de usuário de origem não pode ser o mesmo do usuário destino!");
    }

    private void thenShouldHaveTransferredCorrectly() {
        assertEquals(new BigDecimal("7500"), this.account.getBalance());
        verify(this.accountService, times(1)).save(this.account);

        assertEquals(new BigDecimal("17500"), this.toAccount.getBalance());
        verify(this.accountService, times(1)).save(this.toAccount);
    }

    private void thenShouldHavePaidBillsCorrectly() {
        assertEquals(new BigDecimal("9778"), this.account.getBalance());
        verify(this.accountService, times(1)).save(this.account);
    }

    private void thenShouldExpectErrorCauseTheBillRequestIsNotFilled() {
        this.expectedException.expect(FieldCannotBeNullException.class);
        this.expectedException.expectMessage("Há campos que necessitam de preenchimento para informações de pagamento do boleto!");
    }

    private void thenShouldHaveDepositedCorrectly() {
        assertEquals(new BigDecimal("15000"), this.account.getBalance());
        verify(this.accountService, times(1)).save(this.account);
    }

    private void thenShouldExpectErrorCauseTheValueIsNotFilled() {
        this.expectedException.expect(FieldCannotBeNullException.class);
        this.expectedException.expectMessage("Há campos que necessitam de preenchimento para informações depósito/saque!");
    }

    private void thenShouldExpectErrorCauseTheValueIsNotFilledTransference() {
        this.expectedException.expect(FieldCannotBeNullException.class);
        this.expectedException.expectMessage("Há campos que necessitam de preenchimento para informações de transferência!");

    }

    private void thenShouldExpectErrorCauseTheValueIsNegativeOrZero() {
        this.expectedException.expect(ValueCannotBeNegativeOrZeroException.class);
        this.expectedException.expectMessage("O valor não pode ser zero ou negativo!");
    }

    private void thenShouldHaveDrawnCorrectly() {
        assertEquals(new BigDecimal("5000"), this.account.getBalance());
        verify(this.accountService, times(1)).save(this.account);
    }
}