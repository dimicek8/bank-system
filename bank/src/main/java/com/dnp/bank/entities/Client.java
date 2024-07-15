package com.dnp.bank.entities;

import com.dnp.bank.exceptions.InvalidRateException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private BigDecimal accountBalance;
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
    public void deductFromAccount(BigDecimal amount) {
        if (accountBalance.compareTo(amount) >= 0) {
            accountBalance = accountBalance.subtract(amount);
        } else {
            throw new InvalidRateException("Nedostatečné finanční prostředky!");
        }
    }
}