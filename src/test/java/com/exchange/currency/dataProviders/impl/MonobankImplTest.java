package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.model.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MonobankImplTest {
    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private MonobankImpl monobank = new MonobankImpl(apiClient);
    private List<Exchange> testExchanges;
    private final List<String> desiredCurrencies = List.of("840","978");
    private final String api = "monoTestApi";
    private final String provider = "monobank";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(monobank, "desiredCurrencies", desiredCurrencies);
        ReflectionTestUtils.setField(monobank, "api", api);
        ReflectionTestUtils.setField(monobank, "provider", provider);

        testExchanges = Arrays.asList(
                new Exchange("980", "840", BigDecimal.valueOf(37.5), BigDecimal.valueOf(37.7), new Date(), provider),
                new Exchange("980", "978", BigDecimal.valueOf(43.0), BigDecimal.valueOf(43.2), new Date(), provider)
        );
    }

    @Test
    public void testLoadData() throws Exception {
        when(apiClient.loadData(anyString(), anyString())).thenReturn(testExchanges);

        List<Exchange> result = monobank.loadData();

        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getCurrency());
        assertEquals("UAH", result.get(0).getBaseCurrency());
        assertEquals(BigDecimal.valueOf(37.5), result.get(0).getBuyRate());
        assertEquals(BigDecimal.valueOf(37.7), result.get(0).getSellRate());
    }
}