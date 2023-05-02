package com.exchange.currency.exceptions;

public class ExchangeProviderException extends RuntimeException {
    public ExchangeProviderException(String errorMessage) {
        super(errorMessage);
    }
}
