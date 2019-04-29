package com.es.core.dao;

import com.es.core.model.phone.Price;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class PriceDaoImpl implements PriceDao {
    @Override
    public Set<Price> getPrices(Long phoneId) {

        return null;
    }

    @Override
    public void savePrices(Long phoneId, Set<Price> priceSet) {

    }
}
