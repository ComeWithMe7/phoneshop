package com.es.core.model.phone.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such product")
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
