package com.es.core.model.phone.service;

public interface ProductService {
    public PhoneDaoAnswer findProducts(String sortParam, String gradation, String searchLine, int offset, int limit);
    public PhoneDaoAnswer setPages(PhoneDaoAnswer phoneDaoAnswer, Integer pageNumber, int limitQuantity, int limitPages);
}
