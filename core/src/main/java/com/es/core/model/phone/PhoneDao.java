package com.es.core.model.phone;

import com.es.core.model.phone.service.ProductListAttributes;

import java.util.Map;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    ProductListAttributes findAll(int offset, int limit);
    ProductListAttributes findByTemplate(String template, int offset, int limit);
    ProductListAttributes findAllAndSort(String sortParam, String gradation, int offset, int limit);
    ProductListAttributes findByTemplateAndSort(String template, String sortParam, String gradation, int offset, int limit);
    Long getStock(Long key);
    void updateStock(Map<Long, Long> updatedStock);
}
