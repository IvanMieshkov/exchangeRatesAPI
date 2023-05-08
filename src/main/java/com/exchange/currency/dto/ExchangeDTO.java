package com.exchange.currency.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ExchangeDTO {
    private String baseCurrency;

    private String currency;

    private BigDecimal buyRate;

    private BigDecimal sellRate;

    private Date date;

    private String provider;

    public void setDate(Date date) {
        this.date = new Date();
    }
}
