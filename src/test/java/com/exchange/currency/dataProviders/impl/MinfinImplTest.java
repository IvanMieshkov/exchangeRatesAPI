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
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MinfinImplTest {
    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private MinfinImpl minfin;
    private final List<String> targetLiteral = List.of("usd", "eur");
    private final String baseLiteral = "UAH";
    private final String api = "minfinTestApi";

    private String testResponse = "[{\"id\":\"164813\",\"pointDate\":\"2023-05-10 15:23:30\",\"date\":\"2023-05-10 15:00:00\",\"ask\":\"40.4356\",\"bid\":\"40.0499\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0037\",\"currency\":\"eur\"},{\"id\":\"164808\",\"pointDate\":\"2023-05-10 15:15:50\",\"date\":\"2023-05-10 15:00:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164803\",\"pointDate\":\"2023-05-10 14:40:02\",\"date\":\"2023-05-10 14:30:00\",\"ask\":\"40.4356\",\"bid\":\"40.0462\",\"trendAsk\":\"-0.0185\",\"trendBid\":\"-0.0183\",\"currency\":\"eur\"},{\"id\":\"164798\",\"pointDate\":\"2023-05-10 14:35:03\",\"date\":\"2023-05-10 14:30:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164793\",\"pointDate\":\"2023-05-10 14:17:07\",\"date\":\"2023-05-10 14:00:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164788\",\"pointDate\":\"2023-05-10 14:15:17\",\"date\":\"2023-05-10 14:00:00\",\"ask\":\"40.4541\",\"bid\":\"40.0645\",\"trendAsk\":\"-0.0074\",\"trendBid\":\"0.0036\",\"currency\":\"eur\"},{\"id\":\"164783\",\"pointDate\":\"2023-05-10 13:45:02\",\"date\":\"2023-05-10 13:30:00\",\"ask\":\"40.4615\",\"bid\":\"40.0609\",\"trendAsk\":\"0.0000\",\"trendBid\":\"-0.0146\",\"currency\":\"eur\"},{\"id\":\"164778\",\"pointDate\":\"2023-05-10 13:40:02\",\"date\":\"2023-05-10 13:30:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164773\",\"pointDate\":\"2023-05-10 13:33:11\",\"date\":\"2023-05-10 13:00:00\",\"ask\":\"40.4615\",\"bid\":\"40.0755\",\"trendAsk\":\"0.0074\",\"trendBid\":\"0.0110\",\"currency\":\"eur\"},{\"id\":\"164768\",\"pointDate\":\"2023-05-10 13:33:01\",\"date\":\"2023-05-10 13:00:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164763\",\"pointDate\":\"2023-05-10 12:45:03\",\"date\":\"2023-05-10 12:30:00\",\"ask\":\"40.4541\",\"bid\":\"40.0645\",\"trendAsk\":\"0.0074\",\"trendBid\":\"0.0036\",\"currency\":\"eur\"},{\"id\":\"164758\",\"pointDate\":\"2023-05-10 12:45:02\",\"date\":\"2023-05-10 12:30:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164753\",\"pointDate\":\"2023-05-10 12:24:25\",\"date\":\"2023-05-10 12:00:00\",\"ask\":\"40.4467\",\"bid\":\"40.0609\",\"trendAsk\":\"-0.0222\",\"trendBid\":\"-0.0182\",\"currency\":\"eur\"},{\"id\":\"164748\",\"pointDate\":\"2023-05-10 12:23:11\",\"date\":\"2023-05-10 12:00:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164743\",\"pointDate\":\"2023-05-10 11:45:02\",\"date\":\"2023-05-10 11:30:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164738\",\"pointDate\":\"2023-05-10 11:35:02\",\"date\":\"2023-05-10 11:30:00\",\"ask\":\"40.4689\",\"bid\":\"40.0791\",\"trendAsk\":\"-0.0221\",\"trendBid\":\"-0.0220\",\"currency\":\"eur\"},{\"id\":\"164733\",\"pointDate\":\"2023-05-10 11:21:05\",\"date\":\"2023-05-10 11:00:00\",\"ask\":\"40.4910\",\"bid\":\"40.1011\",\"trendAsk\":\"-0.0591\",\"trendBid\":\"-0.0548\",\"currency\":\"eur\"},{\"id\":\"164728\",\"pointDate\":\"2023-05-10 11:19:39\",\"date\":\"2023-05-10 11:00:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164723\",\"pointDate\":\"2023-05-10 10:45:01\",\"date\":\"2023-05-10 10:30:00\",\"ask\":\"40.5501\",\"bid\":\"40.1559\",\"trendAsk\":\"0.0332\",\"trendBid\":\"0.0365\",\"currency\":\"eur\"},{\"id\":\"164718\",\"pointDate\":\"2023-05-10 10:40:02\",\"date\":\"2023-05-10 10:30:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164708\",\"pointDate\":\"2023-05-10 10:20:12\",\"date\":\"2023-05-10 10:00:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"usd\"},{\"id\":\"164698\",\"pointDate\":\"2023-05-10 10:40:13\",\"date\":\"2023-05-10 10:00:00\",\"ask\":\"40.5169\",\"bid\":\"40.1194\",\"trendAsk\":\"0.0000\",\"trendBid\":\"0.0000\",\"currency\":\"eur\"},{\"id\":\"164713\",\"pointDate\":\"2023-05-10 10:15:03\",\"date\":\"2023-05-10 09:59:00\",\"ask\":\"36.9343\",\"bid\":\"36.5686\",\"trendAsk\":0,\"trendBid\":0,\"currency\":\"usd\"},{\"id\":\"164703\",\"pointDate\":\"2023-05-10 10:10:03\",\"date\":\"2023-05-10 09:59:00\",\"ask\":\"40.5169\",\"bid\":\"40.1194\",\"trendAsk\":0,\"trendBid\":0,\"currency\":\"eur\"}]";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(minfin, "targetLiteral", targetLiteral);
        ReflectionTestUtils.setField(minfin, "baseLiteral", baseLiteral);
        ReflectionTestUtils.setField(minfin, "api", api);
    }

    @Test
    public void testLoadData() throws Exception {
        when(apiClient.getResponse(anyString())).thenReturn(testResponse);

        List<Exchange> result = minfin.loadData();

        assertEquals(2, result.size());
        assertEquals("EUR", result.get(0).getCurrency());
        assertEquals("UAH", result.get(0).getBaseCurrency());
        assertEquals(BigDecimal.valueOf(40.05), result.get(0).getBuyRate().setScale(2, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.valueOf(40.44), result.get(0).getSellRate().setScale(2, RoundingMode.HALF_UP));
    }
}