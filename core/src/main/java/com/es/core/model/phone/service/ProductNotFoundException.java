package com.es.core.model.phone.service;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
