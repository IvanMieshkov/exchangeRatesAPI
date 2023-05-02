package com.exchange.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName(value = "baseCurrency", alternate = {"base_ccy", "currencyCodeB"})
    private String baseCurrency;

    @SerializedName(value = "currency", alternate = {"ccy","currencyCodeA"})
    private String currency;

    @SerializedName(value = "buyRate", alternate = {"buy","rateBuy","bid"})
    private BigDecimal buyRate;

    @SerializedName(value = "sellRate", alternate = {"sale","rateSell","ask"})
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

    @JsonIgnore
    public Long getId() {
        return id;
    }
}
