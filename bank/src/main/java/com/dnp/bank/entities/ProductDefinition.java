package com.dnp.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
public class ProductDefinition {

    @NotNull
    private String operation;
    @Id
    @Size(min = 6, max = 6)
    private String productKey;
    @NotNull
    private String description;
    @NotNull
    private String type;
    @NotNull
    private BigDecimal rate;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "unit", column = @jakarta.persistence.Column(name = "pay_rate_unit")),
            @AttributeOverride(name = "value", column = @jakarta.persistence.Column(name = "pay_rate_value"))
    })
    private PayRate payRate;

    public String getOperation() {
        return operation;
    }
    public String getProductKey() {
        return productKey;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public BigDecimal getRate() {
        return rate;
    }
    public PayRate getPayRate() {
        return payRate;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    public void setPayRate(PayRate payRate) {
        this.payRate = payRate;
    }
}