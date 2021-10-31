package com.example.account.entity;

import com.example.exception.AccountDoesntHaveBalanceException;
import com.example.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Account implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String agency;
    private String accountDigit;
    private String agencyDigit;
    private BigDecimal balance;
    private boolean active;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBalanceGreaterThanZero() {
        return this.balance.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isBalanceGreaterThanWithdrawValue(final BigDecimal withdrawValue) {
        return this.balance.compareTo(withdrawValue) > 0;
    }

    public void withdraw(final BigDecimal value) {
        if (isBalanceGreaterThanZero() && isBalanceGreaterThanWithdrawValue(value)) {
            setBalance(getBalance().subtract(value));
            return;
        }

        throw new AccountDoesntHaveBalanceException("A conta não tem este valor para saque!");
    }

    public void deposit(final BigDecimal value) {
        setBalance(getBalance().add(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return active == account.active && Objects.equals(id, account.id) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(agency, account.agency) && Objects.equals(accountDigit, account.accountDigit) && Objects.equals(agencyDigit, account.agencyDigit) && Objects.equals(balance, account.balance) && Objects.equals(user, account.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, agency, accountDigit, agencyDigit, balance, active, user);
    }
}
