package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.dataProviders.DataProvider;
import com.exchange.currency.dto.MinfinDTO;
import com.exchange.currency.model.Exchange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties("exchange")
public class MinfinImpl implements DataProvider {
    public static final String PROVIDER = "minfin";

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();

    private final ApiClient apiClient;

    private List<String> targetLiteral;

    @Value("${providers.minfin.api}")
    private String api;

    @Value("${exchange.baseLiteral}")
    private String baseLiteral;

    @Override
    public List<Exchange> loadData() throws Exception {
        String response = apiClient.getResponse(api);
        return convertResponse(response);
    }

    private List<Exchange> convertResponse(String response) {
        List<MinfinDTO> rates = gson.fromJson(response, new TypeToken<List<MinfinDTO>>(){}.getType());

        return rates.stream()
                .filter(rate -> targetLiteral.contains(rate.getCurrency()))
                .collect(Collectors.groupingBy(MinfinDTO::getCurrency))
                .values().stream()
                .map(minfinDTOS -> {
                    MinfinDTO minfinDto = minfinDTOS.get(0);
                    Exchange exchange = new Exchange();
                    exchange.setBaseCurrency(baseLiteral);
                    exchange.setCurrency(minfinDto.getCurrency().toUpperCase());
                    exchange.setBuyRate(minfinDto.getBid());
                    exchange.setSellRate(minfinDto.getAsk());
                    exchange.setProvider(PROVIDER);
                    return exchange;
                })
                .collect(Collectors.toList());
    }
}
