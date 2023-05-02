package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.dataProviders.DataProvider;
import com.exchange.currency.model.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrivatbankImpl implements DataProvider {
    @Value("${privatbank.api}")
    private String api;
    @Value("${privatbank.provider}")
    private String provider;
    private ApiClient apiClient;
    @Autowired
    public PrivatbankImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public List<Exchange> loadData() throws Exception {
        return apiClient.loadData(api, provider);
    }
}
