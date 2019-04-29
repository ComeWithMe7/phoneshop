package com.es.core.service;

import com.es.core.model.phone.Phone;

public interface ProductService {
    ProductListAttributes findProducts(String sortParam, String gradation, String searchLine, int offset, int limit);
    ProductListAttributes setPages(ProductListAttributes productListAttributes, Integer pageNumber, int limitQuantity, int limitPages);
    Phone getPhone(long id);
    void update(Phone phone);
}
