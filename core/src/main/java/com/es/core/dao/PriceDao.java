package com.es.core.dao;

import com.es.core.model.phone.Price;

import java.util.Set;

public interface PriceDao {

    Set<Price> getPrices(Long phoneId);
    void savePrices(Long phoneId, Set<Price> priceSet);
}
