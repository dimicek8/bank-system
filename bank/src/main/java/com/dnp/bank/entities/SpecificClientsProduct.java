package com.dnp.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class SpecificClientsProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long clientsId;
    @NotNull
    private String productKey;
    @NotNull
    private BigDecimal clientsRate;
    @NotNull
    private LocalDate clientsContractDate;
    @NotNull
    private String type;
    @Embedded
    private PayRate payRate;

    private BigDecimal originalAmount;
    private int totalInstallments;

    public Long getId() {
        return id;
    }
    public Long getClientsId() {
        return clientsId;
    }
    public String getProductKey() {
        return productKey;
    }
    public BigDecimal getClientsRate() {
        return clientsRate;
    }
    public LocalDate getClientsContractDate() {
        return clientsContractDate;
    }
    public PayRate getPayRate() {
        return payRate;
    }
    public String getType() {
        return type;
    }
    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }
    public int getTotalInstallments() {
        return totalInstallments;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setClientsId(Long clientsId) {
        this.clientsId = clientsId;
    }
    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }
    public void setClientsRate(BigDecimal clientsRate) {
        this.clientsRate = clientsRate;
    }
    public void setClientsContractDate(LocalDate clientsContractDate) {
        this.clientsContractDate = clientsContractDate;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setPayRate(PayRate payRate) {
        this.payRate = payRate;
    }
    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }
    public void setTotalInstallments(int totalInstallments) {
        this.totalInstallments = totalInstallments;
    }
}
