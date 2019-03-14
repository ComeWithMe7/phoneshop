package com.es.core.model.phone;

import com.es.core.model.phone.service.ProductListAttributes;

import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    public ProductListAttributes findAll(int offset, int limit);
    public ProductListAttributes findByTemplate(String template, int offset, int limit);
    public ProductListAttributes findAllAndSort(String sortParam, String gradation, int offset, int limit);
    public ProductListAttributes findByTemplateAndSort(String template, String sortParam, String gradation, int offset, int limit);
}
