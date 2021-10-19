package com.example.account.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Account implements Serializable {

    @Id @GeneratedValue
    private long id;

    private String accountNumber;
    private String agency;
    private String accountDigit;
    private String agencyDigit;
    private BigDecimal balance;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(agency, account.agency) && Objects.equals(accountDigit, account.accountDigit) && Objects.equals(agencyDigit, account.agencyDigit) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, agency, accountDigit, agencyDigit, balance);
    }
}
