package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.dataProviders.DataProvider;
import com.exchange.currency.model.Exchange;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

@Component
@Getter
@Setter
public class MonobankImpl implements DataProvider {
    public static final Set<Currency> currencies = Currency.getAvailableCurrencies();
    public static final String UAH = "980";
    public static final String BASE_CURRENCY = "UAH";
    @Value("#{'${desired.currencies.codes}'.split(',')}")
    private List<String> desiredCurrencies;
    @Value("${monobank.api}")
    private String api;
    @Value("${monobank.provider}")
    public String provider;
    @Autowired
    public MonobankImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    private ApiClient apiClient;
    @Override
    public List<Exchange> loadData() throws Exception {
        List<Exchange> exchanges = apiClient.loadData(api, provider);
        return convertData(exchanges);
    }

    private List<Exchange> convertData(List<Exchange> exchanges) {
        List<Exchange> result = new ArrayList<>();
        for (Exchange exchange : exchanges) {
            if (exchange.getBaseCurrency().equals(UAH) &&
                    desiredCurrencies.contains(exchange.getCurrency())) {
                exchange.setCurrency(
                        currencies.stream()
                                .filter(c -> String.valueOf(c.getNumericCode()).equals(exchange.getCurrency()))
                                .findFirst()
                                .map(Currency::getCurrencyCode).orElseThrow());
                exchange.setBaseCurrency(BASE_CURRENCY);
                result.add(exchange);
            }
        }
        return result;
    }
}
