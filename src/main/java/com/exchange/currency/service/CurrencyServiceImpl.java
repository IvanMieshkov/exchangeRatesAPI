package com.exchange.currency.service;

import com.exchange.currency.dataProviders.DataProvider;
import com.exchange.currency.model.Exchange;
import com.exchange.currency.repository.ExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CurrencyServiceImpl implements CurrencyService {
    public static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    public static final String AVERAGE = "AVERAGE";
    ExchangeRepository exchangeRepository;
    List<DataProvider> providers;
    @Autowired
    public CurrencyServiceImpl(ExchangeRepository exchangeRepository, List<DataProvider> providers) {
        this.exchangeRepository = exchangeRepository;
        this.providers = providers;
    }

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

//    @Scheduled(cron = "0 0/30 * * * ?", zone = "Europe/Kiev")
    @Scheduled(cron = "*/10 * * * * *", zone = "Europe/Kiev")
    public void collectData() {
        List<Exchange> result = new ArrayList<>();
        List<Exchange> recentExchanges = exchangeRepository.findRecentExchangesByProviders();
        for (DataProvider provider : providers) {
            try {
                List<Exchange> exchanges = provider.loadData();
                roundRateValues(exchanges);
                result.addAll(exchanges.stream()
                        .filter(exchange -> recentExchanges.stream()
                                .noneMatch(recentExchange -> exchange.getCurrency().equals(recentExchange.getCurrency()) &&
                                        exchange.getBuyRate().compareTo(recentExchange.getBuyRate()) == 0 &&
                                        exchange.getSellRate().compareTo(recentExchange.getSellRate()) == 0))
                        .toList());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        exchangeRepository.saveAll(result);
    }

    private void roundRateValues(List<Exchange> exchanges) {
        int decimalPlaces = 2;
        for (Exchange exchange : exchanges) {
            BigDecimal sellRate = exchange.getSellRate().setScale(decimalPlaces, RoundingMode.HALF_UP);
            BigDecimal buyRate = exchange.getBuyRate().setScale(decimalPlaces, RoundingMode.HALF_UP);
            exchange.setSellRate(sellRate);
            exchange.setBuyRate(buyRate);
        }
    }

    private List<Exchange> countAverage(List<Exchange> exchanges) {
        Map<String, Map<String, List<Exchange>>> exchangesByCurrency =
                exchanges.stream().collect(Collectors.groupingBy(Exchange::getCurrency,
                        Collectors.groupingBy(Exchange::getBaseCurrency)));

        List<Exchange> result = new ArrayList<>();
        exchangesByCurrency.forEach(
                (currency, baseCurrencyMap) -> baseCurrencyMap.forEach((baseCurrency, exchangeList) -> {
            BigDecimal avgSellRate = exchangeList.stream()
                    .map(Exchange::getSellRate)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(exchangeList.size()), RoundingMode.HALF_UP);
            BigDecimal avgBuyRate = exchangeList.stream()
                    .map(Exchange::getBuyRate)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(exchangeList.size()), RoundingMode.HALF_UP);
            result.add(new Exchange(baseCurrency, currency, avgBuyRate, avgSellRate,
                    new Date(System.currentTimeMillis()), AVERAGE));
        }));
        return result;
    }
}
