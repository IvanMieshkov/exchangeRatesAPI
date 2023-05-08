package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import com.exchange.currency.dto.MinfinDTO;
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

class MinfinImplTest {
    @Mock
    private ApiClient apiClient;
    @Mock
    private MinfinDTO minfinDTO;
    @InjectMocks
    private MinfinImpl minfin;
    private final List<String> targetLiteral = List.of("usd", "eur");
    private final String baseLiteral = "UAH";
    private final String api = "minfinTestApi";
    private final String provider = "minfin";

    private String testResponse = "{\n" +
            "        \"id\": \"163573\",\n" +
            "        \"pointDate\": \"2023-04-28 17:31:01\",\n" +
            "        \"date\": \"2023-04-28 17:00:00\",\n" +
            "        \"ask\": \"36.9343\",\n" +
            "        \"bid\": \"36.5686\",\n" +
            "        \"trendAsk\": \"0.0000\",\n" +
            "        \"trendBid\": \"0.0000\",\n" +
            "        \"currency\": \"usd\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"163568\",\n" +
            "        \"pointDate\": \"2023-04-28 17:31:01\",\n" +
            "        \"date\": \"2023-04-28 17:00:00\",\n" +
            "        \"ask\": \"40.6240\",\n" +
            "        \"bid\": \"40.2364\",\n" +
            "        \"trendAsk\": \"-0.0332\",\n" +
            "        \"trendBid\": \"-0.0183\",\n" +
            "        \"currency\": \"eur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"163563\",\n" +
            "        \"pointDate\": \"2023-04-28 16:40:02\",\n" +
            "        \"date\": \"2023-04-28 16:30:00\",\n" +
            "        \"ask\": \"40.6572\",\n" +
            "        \"bid\": \"40.2547\",\n" +
            "        \"trendAsk\": \"0.0997\",\n" +
            "        \"trendBid\": \"0.0988\",\n" +
            "        \"currency\": \"eur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"163558\",\n" +
            "        \"pointDate\": \"2023-04-28 16:35:02\",\n" +
            "        \"date\": \"2023-04-28 16:30:00\",\n" +
            "        \"ask\": \"36.9343\",\n" +
            "        \"bid\": \"36.5686\",\n" +
            "        \"trendAsk\": \"0.0000\",\n" +
            "        \"trendBid\": \"0.0000\",\n" +
            "        \"currency\": \"usd\"\n" +
            "    }";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(minfin, "targetLiteral", targetLiteral);
        ReflectionTestUtils.setField(minfin, "baseLiteral", baseLiteral);
        ReflectionTestUtils.setField(minfin, "api", api);
    }

    @Disabled
    @Test
    public void testLoadData() throws Exception {
        when(apiClient.getResponse(anyString())).thenReturn(testResponse);

        List<Exchange> result = minfin.loadData();

        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getCurrency());
        assertEquals("UAH", result.get(0).getBaseCurrency());
        assertEquals(BigDecimal.valueOf(36.93), result.get(0).getBuyRate());
        assertEquals(BigDecimal.valueOf(36.56), result.get(0).getSellRate());
    }
}