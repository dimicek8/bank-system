package com.dnp.bank.exceptions;

public class InvalidRateException  extends RuntimeException{
    public InvalidRateException(String message) {
        super(message);
    }
}
