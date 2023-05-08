package com.exchange.currency.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class MonobankDTO {
    private Integer currencyCodeA;

    private Integer currencyCodeB;

    private Date date;

    private BigDecimal rateSell;

    private BigDecimal rateBuy;

    private BigDecimal rateCross;
}
