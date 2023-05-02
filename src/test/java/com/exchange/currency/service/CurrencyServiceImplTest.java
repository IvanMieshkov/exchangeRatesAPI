package com.exchange.currency.service;

import com.exchange.currency.model.Exchange;
import com.exchange.currency.repository.ExchangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CurrencyServiceImplTest {
    @InjectMocks
    private CurrencyServiceImpl currencyService;
    @Mock
    private ExchangeRepository exchangeRepository;
    private List<Exchange> testExchanges;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testExchanges = Arrays.asList(
                new Exchange("UAH", "USD", BigDecimal.valueOf(36.50), BigDecimal.valueOf(36.90), new Date(), "Provider1"),
                new Exchange("UAH", "EUR", BigDecimal.valueOf(40.20), BigDecimal.valueOf(40.60), new Date(), "Provider1"),
                new Exchange("UAH", "USD", BigDecimal.valueOf(35.10), BigDecimal.valueOf(37.20), new Date(), "Provider2"),
                new Exchange("UAH", "EUR", BigDecimal.valueOf(41.0), BigDecimal.valueOf(41.20), new Date(), "Provider2"));
    }

    @Test
    public void testGetCurrentRates() {
        when(exchangeRepository.findRecentExchangesByProviders()).thenReturn(testExchanges);

        List<Exchange> result = currencyService.getCurrentRates();

        assertEquals(testExchanges.size() + 2, result.size());
    }

    @Test
    public void testGetRatesInPeriod() {
        Date dateFrom = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // yesterday
        Date dateTo = new Date();

        when(exchangeRepository.findAllByCreatedDateBetween(dateFrom, dateTo)).thenReturn(testExchanges);
        List<Exchange> result = currencyService.getRatesInPeriod(dateFrom, dateTo);

        assertEquals(2, result.size());
    }
}