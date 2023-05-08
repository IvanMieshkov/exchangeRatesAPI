package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.model.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PrivatbankImplTest {
    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private PrivatbankImpl privatbank;
    private final String api = "privatTestApi";
    private final String testResponse = "[{\"ccy\":\"EUR\",\"base_ccy\":\"UAH\",\"buy\":\"40.50000\",\"sale\":\"41.50000\"},{\"ccy\":\"USD\",\"base_ccy\":\"UAH\",\"buy\":\"37.02000\",\"sale\":\"37.52000\"}]";
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(privatbank, "api", api);
    }

    @Disabled
    @Test
    public void testLoadData() throws Exception {
        when(apiClient.getResponse(anyString())).thenReturn(testResponse);

        List<Exchange> result = privatbank.loadData();

        assertEquals(2, result.size());
        assertEquals("EUR", result.get(0).getCurrency());
        assertEquals("UAH", result.get(0).getBaseCurrency());
        assertEquals(BigDecimal.valueOf(40.50), result.get(0).getBuyRate());
        assertEquals(BigDecimal.valueOf(41.50), result.get(0).getSellRate());
    }
}