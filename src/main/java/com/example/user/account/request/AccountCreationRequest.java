package com.example.user.account.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class AccountCreationRequest implements Serializable {

    public static final long serialVersionUID = 1L;

    private String accountNumber;
    private String agency;
    private String accountDigit;
    private String agencyDigit;
    private BigDecimal balance;

    public AccountCreationRequest() {
    }

    public AccountCreationRequest(final String accountNumber,
                                  final String agency,
                                  final String accountDigit,
                                  final String agencyDigit,
                                  final BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.accountDigit = accountDigit;
        this.agencyDigit = agencyDigit;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(final String agency) {
        this.agency = agency;
    }

    public String getAccountDigit() {
        return accountDigit;
    }

    public void setAccountDigit(final String accountDigit) {
        this.accountDigit = accountDigit;
    }

    public String getAgencyDigit() {
        return agencyDigit;
    }

    public void setAgencyDigit(final String agencyDigit) {
        this.agencyDigit = agencyDigit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCreationRequest that = (AccountCreationRequest) o;
        return Objects.equals(accountNumber, that.accountNumber) && Objects.equals(agency, that.agency) && Objects.equals(accountDigit, that.accountDigit) && Objects.equals(agencyDigit, that.agencyDigit) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, agency, accountDigit, agencyDigit, balance);
    }

    @Override
    public String toString() {
        return "AccountCreationRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", agency='" + agency + '\'' +
                ", accountDigit='" + accountDigit + '\'' +
                ", agencyDigit='" + agencyDigit + '\'' +
                ", balance=" + balance +
                '}';
    }
}
