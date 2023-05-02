package com.exchange.currency.exceptions;

public class ExchangeException extends RuntimeException {
    public ExchangeException(String errorMessage) {
        super(errorMessage);
    }
}
