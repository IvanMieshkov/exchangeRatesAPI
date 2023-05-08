package com.exchange.currency.service;

import com.exchange.currency.dataProviders.DataProvider;
import com.exchange.currency.model.Exchange;
import com.exchange.currency.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private static final String AVERAGE = "AVERAGE";
    private final ExchangeRepository exchangeRepository;
    private final List<DataProvider> providers;

    @Override
    public List<Exchange> getCurrentRates() {
        List<Exchange> result = new ArrayList<>();
        List<Exchange> exchanges = exchangeRepository.findRecentExchangesByProviders();
        result.addAll(exchanges);
        result.addAll(countAverage(exchanges));
        return result;
    }

    @Override
    public List<Exchange> getRatesInPeriod(Date dateFrom, Date dateTo) {
        List<Exchange> exchanges = exchangeRepository.findAllByCreatedDateBetween(dateFrom, dateTo);
        return countAverage(exchanges);
    }
    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 0/30 * * * ?", zone = "Europe/Kiev")
    public void collectData() {
        List<Exchange> result = new ArrayList<>();
        List<Exchange> recentExchanges = exchangeRepository.findRecentExchangesByProviders();
        for (DataProvider provider : providers) {
            try {
                List<Exchange> exchanges = provider.loadData();
                result.addAll(exchanges.stream()
                        .peek(exchange -> {
                            exchange.setSellRate(exchange.getSellRate().setScale(2, RoundingMode.HALF_UP));
                            exchange.setBuyRate(exchange.getBuyRate().setScale(2, RoundingMode.HALF_UP));
                        })
                        .filter(exchange -> recentExchanges.stream()
                                .noneMatch(exchange::hasSameValue))
                        .toList());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        exchangeRepository.saveAll(result);
    }

    private List<Exchange> countAverage(List<Exchange> exchanges) {
        Map<String, Map<String, List<Exchange>>> exchangesByCurrency = collectExchangesByCurrency(exchanges);

        List<Exchange> result = new ArrayList<>();

        exchangesByCurrency.forEach(
                (currency, baseCurrencyMap) -> baseCurrencyMap.forEach((baseCurrency, exchangeList) -> {
            BigDecimal avgSellRate = getAvgSellRate(exchangeList);
            BigDecimal avgBuyRate = getAvgBuyRate(exchangeList);
            result.add(new Exchange(baseCurrency, currency, avgBuyRate, avgSellRate,
                    new Date(System.currentTimeMillis()), AVERAGE));
        }));
        return result;
    }

    private Map<String, Map<String, List<Exchange>>> collectExchangesByCurrency(List<Exchange> exchanges) {
        return exchanges.stream().collect(Collectors.groupingBy(Exchange::getCurrency,
                Collectors.groupingBy(Exchange::getBaseCurrency)));
    }

    private BigDecimal getAvgBuyRate(List<Exchange> exchangeList) {
        return exchangeList.stream()
                .map(Exchange::getBuyRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(exchangeList.size()), RoundingMode.HALF_UP);
    }

    private BigDecimal getAvgSellRate(List<Exchange> exchangeList) {
        return exchangeList.stream()
                .map(Exchange::getSellRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(exchangeList.size()), RoundingMode.HALF_UP);
    }
}
