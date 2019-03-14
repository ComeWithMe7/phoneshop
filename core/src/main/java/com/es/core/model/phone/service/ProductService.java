package com.es.core.model.phone.service;

public interface ProductService {
    public ProductListAttributes findProducts(String sortParam, String gradation, String searchLine, int offset, int limit);
    public ProductListAttributes setPages(ProductListAttributes productListAttributes, Integer pageNumber, int limitQuantity, int limitPages);
}
