package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.dataProviders.DataProvider;
import com.exchange.currency.dto.PrivatbankDTO;
import com.exchange.currency.model.Exchange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PrivatbankImpl implements DataProvider {
    public static final String PROVIDER = "privatbank";
    private final Gson gson = new GsonBuilder().create();
    private final ApiClient apiClient;

    @Value("${providers.privatbank.api}")
    private String api;

    @Override
    public List<Exchange> loadData() throws Exception {
        String response = apiClient.getResponse(api);
        return convertResponse(response);
    }

    private List<Exchange> convertResponse(String response) {
        List<PrivatbankDTO> rates = gson.fromJson(response, new TypeToken<List<PrivatbankDTO>>(){}.getType());

        return rates.stream()
                .map(entry -> {
                    Exchange exchange = new Exchange();
                    exchange.setBaseCurrency(entry.getBase_ccy());
                    exchange.setCurrency(entry.getCcy());
                    exchange.setBuyRate(entry.getBuy());
                    exchange.setSellRate(entry.getSale());
                    exchange.setProvider(PROVIDER);
                    return exchange;
                })
                .collect(Collectors.toList());
    }
}
