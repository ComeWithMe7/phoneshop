package com.es.core.order;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
