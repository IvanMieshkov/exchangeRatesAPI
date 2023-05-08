package com.exchange.currency.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PrivatbankDTO {
    private String ccy;

    private String base_ccy;

    private BigDecimal buy;

    private BigDecimal sale;
}
