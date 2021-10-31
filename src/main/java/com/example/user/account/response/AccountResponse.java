package com.example.user.account.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class AccountResponse implements Serializable {

    public static final long serialVersionUID = 1L;

    private String accountNumber;
    private String agency;
    private String accountDigit;
    private String agencyDigit;
    private BigDecimal balance;
    private boolean active;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAccountDigit() {
        return accountDigit;
    }

    public void setAccountDigit(String accountDigit) {
        this.accountDigit = accountDigit;
    }

    public String getAgencyDigit() {
        return agencyDigit;
    }

    public void setAgencyDigit(String agencyDigit) {
        this.agencyDigit = agencyDigit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountResponse that = (AccountResponse) o;
        return active == that.active && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(agency, that.agency) && Objects.equals(accountDigit, that.accountDigit) && Objects.equals(agencyDigit, that.agencyDigit) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, agency, accountDigit, agencyDigit, balance, active);
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "accountNumber='" + accountNumber + '\'' +
                ", agency='" + agency + '\'' +
                ", accountDigit='" + accountDigit + '\'' +
                ", agencyDigit='" + agencyDigit + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                '}';
    }
}
