package com.es.core.model.phone;

import java.math.BigDecimal;

public class Price {

    private Currency currency;

    private BigDecimal price;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
