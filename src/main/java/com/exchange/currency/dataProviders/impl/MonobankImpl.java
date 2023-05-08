package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.dataProviders.DataProvider;
import com.exchange.currency.dto.MonobankDTO;
import com.exchange.currency.model.Exchange;
import com.exchange.currency.util.UnixTimestampDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties("exchange")
public class MonobankImpl implements DataProvider {
    public static final String PROVIDER = "monobank";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new UnixTimestampDeserializer()).create();
    private final ApiClient apiClient;
    private List<Integer> targetCode;

    @Value("${providers.monobank.api}")
    private String api;

    @Value("${exchange.baseLiteral}")
    private String baseLiteral;

    @Value("${exchange.baseCode}")
    private String baseCode;

    @Override
    public List<Exchange> loadData() throws Exception {
        String response = apiClient.getResponse(api);

        return convertResponse(response);
    }

    private List<Exchange> convertResponse(String response) {
        List<MonobankDTO> rates = gson.fromJson(response, new TypeToken<List<MonobankDTO>>(){}.getType());

        Map<Integer, String> currencyMap = new HashMap<>();
        for (Currency currency : Currency.getAvailableCurrencies()) {
            currencyMap.put(currency.getNumericCode(), currency.getCurrencyCode());
        }

        return rates.stream()
                .filter(dto -> dto.getCurrencyCodeB() == Integer.parseInt(baseCode) &&
                        targetCode.contains(dto.getCurrencyCodeA()))
                .map(dto -> {
                    Exchange exchange = new Exchange();
                    exchange.setBaseCurrency(baseLiteral);
                    exchange.setCurrency(currencyMap.get(dto.getCurrencyCodeA()));
                    exchange.setBuyRate(dto.getRateBuy());
                    exchange.setSellRate(dto.getRateSell());
                    exchange.setProvider(PROVIDER);
                    return exchange;
                })
                .distinct()
                .collect(Collectors.toList());


    }
}
