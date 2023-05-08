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

class MonobankImplTest {
    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private MonobankImpl monobank;
    private String baseCode = "980";
    private List<String> targetCode = List.of("840", "978");
    private String baseLiteral = "UAH";
    private String api = "monoTestApi";
    private String testResponse = "[{\"currencyCodeA\":840,\"currencyCodeB\":980,\"date\":1683496874,\"rateBuy\":36.65,\"rateCross\":0,\"rateSell\":37.4406},{\"currencyCodeA\":978,\"currencyCodeB\":980,\"date\":1683564607,\"rateBuy\":40.4,\"rateCross\":0,\"rateSell\":41.5507},{\"currencyCodeA\":978,\"currencyCodeB\":840,\"date\":1683496874,\"rateBuy\":1.096,\"rateCross\":0,\"rateSell\":1.106},{\"currencyCodeA\":826,\"currencyCodeB\":980,\"date\":1683574249,\"rateBuy\":0,\"rateCross\":47.4246,\"rateSell\":0}";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(monobank, "targetCode", targetCode);
        ReflectionTestUtils.setField(monobank, "baseCode", baseCode);
        ReflectionTestUtils.setField(monobank, "baseLiteral", baseLiteral);
        ReflectionTestUtils.setField(monobank, "api", api);
    }

    @Disabled
    @Test
    public void testLoadData() throws Exception {
        when(apiClient.getResponse(anyString())).thenReturn(testResponse);

        List<Exchange> result = monobank.loadData();

        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getCurrency());
        assertEquals("UAH", result.get(0).getBaseCurrency());
        assertEquals(BigDecimal.valueOf(36.65), result.get(0).getBuyRate());
        assertEquals(BigDecimal.valueOf(37.44), result.get(0).getSellRate());
    }
}