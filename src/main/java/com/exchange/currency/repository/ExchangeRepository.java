package com.exchange.currency.repository;

import com.exchange.currency.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findAllByCreatedDateBetween(Date dateFrom, Date dateTo);

    @Query(nativeQuery = true,
            value = "SELECT e.id, e.provider, e.currency, e.base_currency, e.created_date, e.sell_rate, e.buy_rate " +
            "FROM exchanges e " +
            "INNER JOIN (" +
            "SELECT e1.currency, e1.provider, MAX(e1.created_date) AS latest_timestamp " +
            "FROM exchanges e1 " +
            "GROUP BY e1.currency, e1.provider" +
            ") latest_data " +
            "ON e.currency = latest_data.currency " +
            "AND e.provider = latest_data.provider " +
            "AND e.created_date = latest_data.latest_timestamp " +
            "GROUP BY e.id, e.provider, e.currency, e.sell_rate, e.buy_rate")
    List<Exchange> findRecentExchangesByProviders();

}