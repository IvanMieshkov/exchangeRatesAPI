package com.exchange.currency.dataProviders;

import com.exchange.currency.model.Exchange;

import java.util.List;

public interface DataProvider {

    List<Exchange> loadData() throws Exception;
}
