package com.exchange.currency.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class MinfinDTO {
    private Long id;

    private Date pointDate;

    private Date date;

    private BigDecimal ask;

    private BigDecimal bid;

    private BigDecimal trendAsk;

    private BigDecimal trendBid;

    private String currency;
}
