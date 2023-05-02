package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.dataProviders.DataProvider;
import com.exchange.currency.exceptions.ExchangeException;
import com.exchange.currency.model.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MinfinImpl implements DataProvider {
    public static final String BASE_CURRENCY = "UAH";
    @Value("#{'${desired.currencies}'.split(',')}")
    private List<String> desiredCurrencies;
    @Value("${minfin.api}")
    private String api;
    @Value("${minfin.provider}")
    private String provider;
    private ApiClient apiClient;

    @Autowired
    public MinfinImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    @Override
    public List<Exchange> loadData() throws Exception {
        List<Exchange> exchanges = apiClient.loadData(api, provider);
        return convertData(exchanges);
    }

    private List<Exchange> convertData(List<Exchange> exchanges) {
        List<Exchange> result = new ArrayList<>();

        for (String desiredCurrency : desiredCurrencies) {
            result.add(exchanges.stream()
                    .filter(e -> e.getCurrency().equals(desiredCurrency))
                    .findFirst()
                    .orElseThrow(() ->
                            new ExchangeException("Currency not found. Source: " + provider + ", Currency: " + desiredCurrency)));
        }
        for (Exchange exchange : result) {
            exchange.setCurrency(exchange.getCurrency().toUpperCase());
            exchange.setBaseCurrency(BASE_CURRENCY);
        }
        return result;
    }
}
