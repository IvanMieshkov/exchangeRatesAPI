package com.exchange.currency.controller;

import com.exchange.currency.dto.ExchangeDTO;
import com.exchange.currency.model.Exchange;
import com.exchange.currency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping("/rates")
    public List<ExchangeDTO> getCurrentRates() {
        List<Exchange> currentRates = currencyServiceImpl.getCurrentRates();

        return currentRates.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/rates/period")
    public List<ExchangeDTO> getRatesInPeriod(@RequestParam @DateTimeFormat(iso = DATE) Date dateFrom,
                                           @RequestParam @DateTimeFormat(iso = DATE) Date dateTo) {
        List<Exchange> ratesInPeriod = currencyServiceImpl.getRatesInPeriod(dateFrom, dateTo);

        return ratesInPeriod.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ExchangeDTO convertToDto(Exchange exchange) {
         return modelMapper.map(exchange, ExchangeDTO.class);
    }
}
