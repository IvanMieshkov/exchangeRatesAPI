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

class MonobankImplTest {
    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private MonobankImpl monobank;
    private String baseCode = "980";
    private List<Integer> targetCode = List.of(840, 978);
    private String baseLiteral = "UAH";
    private String api = "monoTestApi";
    private String testResponse = "[{\"currencyCodeA\":840,\"currencyCodeB\":980,\"date\":1683583274,\"rateBuy\":36.65,\"rateCross\":0,\"rateSell\":37.4406},{\"currencyCodeA\":978,\"currencyCodeB\":980,\"date\":1683637207,\"rateBuy\":40.2,\"rateCross\":0,\"rateSell\":41.3993},{\"currencyCodeA\":978,\"currencyCodeB\":840,\"date\":1683637207,\"rateBuy\":1.092,\"rateCross\":0,\"rateSell\":1.106},{\"currencyCodeA\":826,\"currencyCodeB\":980,\"date\":1683641311,\"rateBuy\":0,\"rateCross\":47.4379,\"rateSell\":0},{\"currencyCodeA\":392,\"currencyCodeB\":980,\"date\":1683640486,\"rateBuy\":0,\"rateCross\":0.278,\"rateSell\":0},{\"currencyCodeA\":756,\"currencyCodeB\":980,\"date\":1683641291,\"rateBuy\":0,\"rateCross\":42.2072,\"rateSell\":0},{\"currencyCodeA\":156,\"currencyCodeB\":980,\"date\":1683641263,\"rateBuy\":0,\"rateCross\":5.4196,\"rateSell\":0},{\"currencyCodeA\":784,\"currencyCodeB\":980,\"date\":1683641265,\"rateBuy\":0,\"rateCross\":10.1974,\"rateSell\":0},{\"currencyCodeA\":971,\"currencyCodeB\":980,\"date\":1663425223,\"rateBuy\":0,\"rateCross\":0.4252,\"rateSell\":0},{\"currencyCodeA\":8,\"currencyCodeB\":980,\"date\":1683641273,\"rateBuy\":0,\"rateCross\":0.372,\"rateSell\":0},{\"currencyCodeA\":51,\"currencyCodeB\":980,\"date\":1683641158,\"rateBuy\":0,\"rateCross\":0.0972,\"rateSell\":0},{\"currencyCodeA\":973,\"currencyCodeB\":980,\"date\":1683565821,\"rateBuy\":0,\"rateCross\":0.0737,\"rateSell\":0},{\"currencyCodeA\":32,\"currencyCodeB\":980,\"date\":1683641310,\"rateBuy\":0,\"rateCross\":0.1646,\"rateSell\":0},{\"currencyCodeA\":36,\"currencyCodeB\":980,\"date\":1683640399,\"rateBuy\":0,\"rateCross\":25.46,\"rateSell\":0},{\"currencyCodeA\":944,\"currencyCodeB\":980,\"date\":1683641095,\"rateBuy\":0,\"rateCross\":22.1048,\"rateSell\":0},{\"currencyCodeA\":50,\"currencyCodeB\":980,\"date\":1683640841,\"rateBuy\":0,\"rateCross\":0.3501,\"rateSell\":0},{\"currencyCodeA\":975,\"currencyCodeB\":980,\"date\":1683641294,\"rateBuy\":0,\"rateCross\":21.1636,\"rateSell\":0},{\"currencyCodeA\":48,\"currencyCodeB\":980,\"date\":1683640423,\"rateBuy\":0,\"rateCross\":99.31,\"rateSell\":0},{\"currencyCodeA\":108,\"currencyCodeB\":980,\"date\":1538606522,\"rateBuy\":0,\"rateCross\":0.0158,\"rateSell\":0},{\"currencyCodeA\":96,\"currencyCodeB\":980,\"date\":1681712199,\"rateBuy\":0,\"rateCross\":28.363,\"rateSell\":0},{\"currencyCodeA\":68,\"currencyCodeB\":980,\"date\":1683636661,\"rateBuy\":0,\"rateCross\":5.4588,\"rateSell\":0},{\"currencyCodeA\":986,\"currencyCodeB\":980,\"date\":1683641205,\"rateBuy\":0,\"rateCross\":7.5796,\"rateSell\":0},{\"currencyCodeA\":72,\"currencyCodeB\":980,\"date\":1681978995,\"rateBuy\":0,\"rateCross\":2.8521,\"rateSell\":0},{\"currencyCodeA\":933,\"currencyCodeB\":980,\"date\":1683635840,\"rateBuy\":0,\"rateCross\":13.2374,\"rateSell\":0},{\"currencyCodeA\":124,\"currencyCodeB\":980,\"date\":1683641316,\"rateBuy\":0,\"rateCross\":28.1272,\"rateSell\":0},{\"currencyCodeA\":976,\"currencyCodeB\":980,\"date\":1655462332,\"rateBuy\":0,\"rateCross\":0.0163,\"rateSell\":0},{\"currencyCodeA\":152,\"currencyCodeB\":980,\"date\":1683637054,\"rateBuy\":0,\"rateCross\":0.0472,\"rateSell\":0},{\"currencyCodeA\":170,\"currencyCodeB\":980,\"date\":1683639830,\"rateBuy\":0,\"rateCross\":0.0082,\"rateSell\":0},{\"currencyCodeA\":188,\"currencyCodeB\":980,\"date\":1683639979,\"rateBuy\":0,\"rateCross\":0.0694,\"rateSell\":0},{\"currencyCodeA\":192,\"currencyCodeB\":980,\"date\":1683583206,\"rateBuy\":0,\"rateCross\":1.5237,\"rateSell\":0},{\"currencyCodeA\":203,\"currencyCodeB\":980,\"date\":1683641322,\"rateBuy\":0,\"rateCross\":1.7691,\"rateSell\":0},{\"currencyCodeA\":262,\"currencyCodeB\":980,\"date\":1678810696,\"rateBuy\":0,\"rateCross\":0.2108,\"rateSell\":0},{\"currencyCodeA\":208,\"currencyCodeB\":980,\"date\":1683641171,\"rateBuy\":0,\"rateCross\":5.5579,\"rateSell\":0},{\"currencyCodeA\":12,\"currencyCodeB\":980,\"date\":1683640964,\"rateBuy\":0,\"rateCross\":0.2773,\"rateSell\":0},{\"currencyCodeA\":818,\"currencyCodeB\":980,\"date\":1683641004,\"rateBuy\":0,\"rateCross\":1.2143,\"rateSell\":0},{\"currencyCodeA\":230,\"currencyCodeB\":980,\"date\":1683242328,\"rateBuy\":0,\"rateCross\":0.6909,\"rateSell\":0},{\"currencyCodeA\":981,\"currencyCodeB\":980,\"date\":1683641308,\"rateBuy\":0,\"rateCross\":15.2069,\"rateSell\":0},{\"currencyCodeA\":936,\"currencyCodeB\":980,\"date\":1683638233,\"rateBuy\":0,\"rateCross\":3.1782,\"rateSell\":0},{\"currencyCodeA\":270,\"currencyCodeB\":980,\"date\":1683312957,\"rateBuy\":0,\"rateCross\":0.6385,\"rateSell\":0},{\"currencyCodeA\":324,\"currencyCodeB\":980,\"date\":1682634561,\"rateBuy\":0,\"rateCross\":0.0044,\"rateSell\":0},{\"currencyCodeA\":344,\"currencyCodeB\":980,\"date\":1683630318,\"rateBuy\":0,\"rateCross\":4.7776,\"rateSell\":0},{\"currencyCodeA\":191,\"currencyCodeB\":980,\"date\":1680625280,\"rateBuy\":0,\"rateCross\":5.4258,\"rateSell\":0},{\"currencyCodeA\":348,\"currencyCodeB\":980,\"date\":1683641313,\"rateBuy\":0,\"rateCross\":0.1113,\"rateSell\":0},{\"currencyCodeA\":360,\"currencyCodeB\":980,\"date\":1683641308,\"rateBuy\":0,\"rateCross\":0.0025,\"rateSell\":0},{\"currencyCodeA\":376,\"currencyCodeB\":980,\"date\":1683641227,\"rateBuy\":0,\"rateCross\":10.3357,\"rateSell\":0},{\"currencyCodeA\":356,\"currencyCodeB\":980,\"date\":1683641180,\"rateBuy\":0,\"rateCross\":0.4571,\"rateSell\":0},{\"currencyCodeA\":368,\"currencyCodeB\":980,\"date\":1683639242,\"rateBuy\":0,\"rateCross\":0.0285,\"rateSell\":0},{\"currencyCodeA\":364,\"currencyCodeB\":980,\"date\":1683583206,\"rateBuy\":0,\"rateCross\":0.0009,\"rateSell\":0},{\"currencyCodeA\":352,\"currencyCodeB\":980,\"date\":1683641311,\"rateBuy\":0,\"rateCross\":0.2747,\"rateSell\":0},{\"currencyCodeA\":400,\"currencyCodeB\":980,\"date\":1683635592,\"rateBuy\":0,\"rateCross\":52.8691,\"rateSell\":0},{\"currencyCodeA\":404,\"currencyCodeB\":980,\"date\":1683638327,\"rateBuy\":0,\"rateCross\":0.2743,\"rateSell\":0},{\"currencyCodeA\":417,\"currencyCodeB\":980,\"date\":1683639772,\"rateBuy\":0,\"rateCross\":0.4288,\"rateSell\":0},{\"currencyCodeA\":116,\"currencyCodeB\":980,\"date\":1683627667,\"rateBuy\":0,\"rateCross\":0.009,\"rateSell\":0},{\"currencyCodeA\":408,\"currencyCodeB\":980,\"date\":1683583206,\"rateBuy\":0,\"rateCross\":16.6221,\"rateSell\":0},{\"currencyCodeA\":410,\"currencyCodeB\":980,\"date\":1683641188,\"rateBuy\":0,\"rateCross\":0.0284,\"rateSell\":0},{\"currencyCodeA\":414,\"currencyCodeB\":980,\"date\":1683637412,\"rateBuy\":0,\"rateCross\":122.3066,\"rateSell\":0},{\"currencyCodeA\":398,\"currencyCodeB\":980,\"date\":1683641231,\"rateBuy\":0,\"rateCross\":0.0845,\"rateSell\":0},{\"currencyCodeA\":418,\"currencyCodeB\":980,\"date\":1683109688,\"rateBuy\":0,\"rateCross\":0.0021,\"rateSell\":0},{\"currencyCodeA\":422,\"currencyCodeB\":980,\"date\":1683454521,\"rateBuy\":0,\"rateCross\":0.0004,\"rateSell\":0},{\"currencyCodeA\":144,\"currencyCodeB\":980,\"date\":1683639471,\"rateBuy\":0,\"rateCross\":0.1174,\"rateSell\":0},{\"currencyCodeA\":434,\"currencyCodeB\":980,\"date\":1674670757,\"rateBuy\":0,\"rateCross\":7.8783,\"rateSell\":0},{\"currencyCodeA\":504,\"currencyCodeB\":980,\"date\":1683635938,\"rateBuy\":0,\"rateCross\":3.7627,\"rateSell\":0},{\"currencyCodeA\":498,\"currencyCodeB\":980,\"date\":1683641310,\"rateBuy\":0,\"rateCross\":2.1066,\"rateSell\":0},{\"currencyCodeA\":969,\"currencyCodeB\":980,\"date\":1683386174,\"rateBuy\":0,\"rateCross\":0.0085,\"rateSell\":0},{\"currencyCodeA\":807,\"currencyCodeB\":980,\"date\":1683641117,\"rateBuy\":0,\"rateCross\":0.6717,\"rateSell\":0},{\"currencyCodeA\":496,\"currencyCodeB\":980,\"date\":1683628728,\"rateBuy\":0,\"rateCross\":0.0107,\"rateSell\":0},{\"currencyCodeA\":478,\"currencyCodeB\":980,\"date\":1683583206,\"rateBuy\":0,\"rateCross\":0.1071,\"rateSell\":0},{\"currencyCodeA\":480,\"currencyCodeB\":980,\"date\":1683640754,\"rateBuy\":0,\"rateCross\":0.8288,\"rateSell\":0},{\"currencyCodeA\":454,\"currencyCodeB\":980,\"date\":1678369785,\"rateBuy\":0,\"rateCross\":0.0368,\"rateSell\":0},{\"currencyCodeA\":484,\"currencyCodeB\":980,\"date\":1683641139,\"rateBuy\":0,\"rateCross\":2.1091,\"rateSell\":0},{\"currencyCodeA\":458,\"currencyCodeB\":980,\"date\":1683641096,\"rateBuy\":0,\"rateCross\":8.449,\"rateSell\":0},{\"currencyCodeA\":943,\"currencyCodeB\":980,\"date\":1683622015,\"rateBuy\":0,\"rateCross\":0.5919,\"rateSell\":0},{\"currencyCodeA\":516,\"currencyCodeB\":980,\"date\":1683639126,\"rateBuy\":0,\"rateCross\":2.0498,\"rateSell\":0},{\"currencyCodeA\":566,\"currencyCodeB\":980,\"date\":1683638296,\"rateBuy\":0,\"rateCross\":0.0808,\"rateSell\":0},{\"currencyCodeA\":558,\"currencyCodeB\":980,\"date\":1683601411,\"rateBuy\":0,\"rateCross\":1.0288,\"rateSell\":0},{\"currencyCodeA\":578,\"currencyCodeB\":980,\"date\":1683641297,\"rateBuy\":0,\"rateCross\":3.5819,\"rateSell\":0},{\"currencyCodeA\":524,\"currencyCodeB\":980,\"date\":1683636939,\"rateBuy\":0,\"rateCross\":0.286,\"rateSell\":0},{\"currencyCodeA\":554,\"currencyCodeB\":980,\"date\":1683619857,\"rateBuy\":0,\"rateCross\":23.8246,\"rateSell\":0},{\"currencyCodeA\":512,\"currencyCodeB\":980,\"date\":1683610569,\"rateBuy\":0,\"rateCross\":97.1803,\"rateSell\":0},{\"currencyCodeA\":604,\"currencyCodeB\":980,\"date\":1683639019,\"rateBuy\":0,\"rateCross\":10.1514,\"rateSell\":0},{\"currencyCodeA\":608,\"currencyCodeB\":980,\"date\":1683638559,\"rateBuy\":0,\"rateCross\":0.6773,\"rateSell\":0},{\"currencyCodeA\":586,\"currencyCodeB\":980,\"date\":1683641054,\"rateBuy\":0,\"rateCross\":0.132,\"rateSell\":0},{\"currencyCodeA\":985,\"currencyCodeB\":980,\"date\":1683641319,\"rateBuy\":0,\"rateCross\":9.0667,\"rateSell\":0},{\"currencyCodeA\":600,\"currencyCodeB\":980,\"date\":1683640510,\"rateBuy\":0,\"rateCross\":0.0052,\"rateSell\":0},{\"currencyCodeA\":634,\"currencyCodeB\":980,\"date\":1683640246,\"rateBuy\":0,\"rateCross\":10.2877,\"rateSell\":0},{\"currencyCodeA\":946,\"currencyCodeB\":980,\"date\":1683641323,\"rateBuy\":0,\"rateCross\":8.4112,\"rateSell\":0},{\"currencyCodeA\":941,\"currencyCodeB\":980,\"date\":1683641283,\"rateBuy\":0,\"rateCross\":0.3522,\"rateSell\":0},{\"currencyCodeA\":682,\"currencyCodeB\":980,\"date\":1683640276,\"rateBuy\":0,\"rateCross\":9.9846,\"rateSell\":0},{\"currencyCodeA\":690,\"currencyCodeB\":980,\"date\":1683639183,\"rateBuy\":0,\"rateCross\":2.752,\"rateSell\":0},{\"currencyCodeA\":938,\"currencyCodeB\":980,\"date\":1680961561,\"rateBuy\":0,\"rateCross\":0.0627,\"rateSell\":0},{\"currencyCodeA\":752,\"currencyCodeB\":980,\"date\":1683641307,\"rateBuy\":0,\"rateCross\":3.693,\"rateSell\":0},{\"currencyCodeA\":702,\"currencyCodeB\":980,\"date\":1683641306,\"rateBuy\":0,\"rateCross\":28.2911,\"rateSell\":0},{\"currencyCodeA\":694,\"currencyCodeB\":980,\"date\":1664217991,\"rateBuy\":0,\"rateCross\":0.0024,\"rateSell\":0},{\"currencyCodeA\":706,\"currencyCodeB\":980,\"date\":1683386099,\"rateBuy\":0,\"rateCross\":0.0659,\"rateSell\":0},{\"currencyCodeA\":968,\"currencyCodeB\":980,\"date\":1680879569,\"rateBuy\":0,\"rateCross\":1.0284,\"rateSell\":0},{\"currencyCodeA\":760,\"currencyCodeB\":980,\"date\":1683583206,\"rateBuy\":0,\"rateCross\":0.0056,\"rateSell\":0},{\"currencyCodeA\":748,\"currencyCodeB\":980,\"date\":1668614714,\"rateBuy\":0,\"rateCross\":2.1898,\"rateSell\":0},{\"currencyCodeA\":764,\"currencyCodeB\":980,\"date\":1683641313,\"rateBuy\":0,\"rateCross\":1.1087,\"rateSell\":0},{\"currencyCodeA\":972,\"currencyCodeB\":980,\"date\":1683625837,\"rateBuy\":0,\"rateCross\":3.4309,\"rateSell\":0},{\"currencyCodeA\":795,\"currencyCodeB\":980,\"date\":1683583206,\"rateBuy\":0,\"rateCross\":0.0021,\"rateSell\":0},{\"currencyCodeA\":788,\"currencyCodeB\":980,\"date\":1683636684,\"rateBuy\":0,\"rateCross\":12.3601,\"rateSell\":0},{\"currencyCodeA\":949,\"currencyCodeB\":980,\"date\":1683641310,\"rateBuy\":0,\"rateCross\":1.9406,\"rateSell\":0},{\"currencyCodeA\":901,\"currencyCodeB\":980,\"date\":1683641144,\"rateBuy\":0,\"rateCross\":1.2223,\"rateSell\":0},{\"currencyCodeA\":834,\"currencyCodeB\":980,\"date\":1683641085,\"rateBuy\":0,\"rateCross\":0.0159,\"rateSell\":0},{\"currencyCodeA\":800,\"currencyCodeB\":980,\"date\":1683557735,\"rateBuy\":0,\"rateCross\":0.01,\"rateSell\":0},{\"currencyCodeA\":858,\"currencyCodeB\":980,\"date\":1683611589,\"rateBuy\":0,\"rateCross\":0.9677,\"rateSell\":0},{\"currencyCodeA\":860,\"currencyCodeB\":980,\"date\":1683640825,\"rateBuy\":0,\"rateCross\":0.0032,\"rateSell\":0},{\"currencyCodeA\":937,\"currencyCodeB\":980,\"date\":1683583206,\"rateBuy\":0,\"rateCross\":1.4641,\"rateSell\":0},{\"currencyCodeA\":704,\"currencyCodeB\":980,\"date\":1683640999,\"rateBuy\":0,\"rateCross\":0.0016,\"rateSell\":0},{\"currencyCodeA\":950,\"currencyCodeB\":980,\"date\":1683631191,\"rateBuy\":0,\"rateCross\":0.0629,\"rateSell\":0},{\"currencyCodeA\":952,\"currencyCodeB\":980,\"date\":1683630886,\"rateBuy\":0,\"rateCross\":0.0629,\"rateSell\":0},{\"currencyCodeA\":886,\"currencyCodeB\":980,\"date\":1543715495,\"rateBuy\":0,\"rateCross\":0.112,\"rateSell\":0},{\"currencyCodeA\":710,\"currencyCodeB\":980,\"date\":1683641234,\"rateBuy\":0,\"rateCross\":2.0491,\"rateSell\":0},{\"currencyCodeA\":894,\"currencyCodeB\":980,\"date\":1683583206,\"rateBuy\":0,\"rateCross\":0.002,\"rateSell\":0}]";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(monobank, "targetCode", targetCode);
        ReflectionTestUtils.setField(monobank, "baseCode", baseCode);
        ReflectionTestUtils.setField(monobank, "baseLiteral", baseLiteral);
        ReflectionTestUtils.setField(monobank, "api", api);
    }

    @Test
    public void testLoadData() throws Exception {

        when(apiClient.getResponse(anyString())).thenReturn(testResponse);

        List<Exchange> result = monobank.loadData();

        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getCurrency());
        assertEquals("UAH", result.get(0).getBaseCurrency());
        assertEquals(BigDecimal.valueOf(36.65), result.get(0).getBuyRate());
        assertEquals(BigDecimal.valueOf(37.4406).setScale(4, RoundingMode.HALF_UP), result.get(0).getSellRate());
    }
}