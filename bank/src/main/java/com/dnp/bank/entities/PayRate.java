package com.dnp.bank.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class PayRate {

    @NotNull
    private String unit;
    @NotNull
    private int value;

    public String getUnit() {
        return unit;
    }
    public int getValue() {
        return value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
