package com.es.core.order;

import java.util.HashMap;
import java.util.Map;

public class PlaceOrder {

    private boolean successful;

    private Map<Long, String> removedProducts;

    private String hash;

    public PlaceOrder() {
        removedProducts = new HashMap<>();
        successful = true;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Map<Long, String> getRemovedProducts() {
        return removedProducts;
    }

    public void setRemovedProducts(Map<Long, String> removedProducts) {
        this.removedProducts = removedProducts;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
