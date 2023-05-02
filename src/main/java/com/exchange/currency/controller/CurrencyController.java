package com.exchange.currency.controller;

import com.exchange.currency.model.Exchange;
import com.exchange.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyServiceImpl;

    @RequestMapping(value = "/rates", method = RequestMethod.GET)
    public List<Exchange> getCurrentRates() {
        return currencyServiceImpl.getCurrentRates();
    }

    @RequestMapping(value = "/rates/period", method = RequestMethod.GET)
    public List<Exchange> getRatesInPeriod(@RequestParam @DateTimeFormat(iso = DATE) Date dateFrom,
                                           @RequestParam @DateTimeFormat(iso = DATE) Date dateTo) {
        return currencyServiceImpl.getRatesInPeriod(dateFrom, dateTo);
    }
}
