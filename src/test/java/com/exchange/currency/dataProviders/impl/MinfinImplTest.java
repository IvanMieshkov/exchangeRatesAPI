package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.exceptions.ExchangeException;
import com.exchange.currency.model.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MinfinImplTest {
    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private MinfinImpl minfin = new MinfinImpl(apiClient);
    private List<Exchange> testExchanges;
    private final List<String> desiredCurrencies = List.of("usd", "eur");
    private final String api = "minfinTestApi";
    private final String provider = "minfin";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(minfin, "desiredCurrencies", desiredCurrencies);
        ReflectionTestUtils.setField(minfin, "api", api);
        ReflectionTestUtils.setField(minfin, "provider", provider);

        testExchanges = Arrays.asList(
                new Exchange("UAH", "usd", BigDecimal.valueOf(37.5), BigDecimal.valueOf(37.7), new Date(), provider),
                new Exchange("UAH", "eur", BigDecimal.valueOf(43.0), BigDecimal.valueOf(43.2), new Date(), provider)
        );
    }

    @Test
    public void testLoadData() throws Exception {
        when(apiClient.loadData(anyString(), anyString())).thenReturn(testExchanges);

        List<Exchange> result = minfin.loadData();

        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getCurrency());
        assertEquals("UAH", result.get(0).getBaseCurrency());
        assertEquals(BigDecimal.valueOf(37.5), result.get(0).getBuyRate());
        assertEquals(BigDecimal.valueOf(37.7), result.get(0).getSellRate());
    }


    @Test
    public void testLoadDataNotFound() throws Exception {
        when(apiClient.loadData(anyString(), anyString())).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(ExchangeException.class, () -> minfin.loadData());

        String expectedMessage = "Currency not found. Source: minfin, Currency: usd";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }
}