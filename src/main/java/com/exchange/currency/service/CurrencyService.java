package com.exchange.currency.service;

import com.exchange.currency.model.Exchange;

import java.util.Date;
import java.util.List;

public interface CurrencyService {
    List<Exchange> getCurrentRates();

    List<Exchange> getRatesInPeriod(Date dateFrom, Date dateTo);
}
