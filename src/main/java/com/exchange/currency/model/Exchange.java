package com.exchange.currency.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "exchanges")
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baseCurrency;

    private String currency;

    private BigDecimal buyRate;

    private BigDecimal sellRate;

    @CreationTimestamp
    private Date createdDate;

    private String provider;

    public Exchange(String baseCurrency, String currency, BigDecimal buyRate,
                    BigDecimal sellRate, Date createdDate, String provider) {
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.createdDate = createdDate;
        this.provider = provider;
    }

    public boolean hasSameValue(Exchange exchange) {
        return this.getCurrency().equals(exchange.getCurrency()) &&
                this.getBuyRate().compareTo(exchange.getBuyRate()) == 0 &&
                this.getSellRate().compareTo(exchange.getSellRate()) == 0;
    }
}
