package com.es.core.model.phone;

import com.es.core.model.phone.service.PhoneDaoAnswer;

import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    public PhoneDaoAnswer findAll(int offset, int limit);
    public PhoneDaoAnswer findByTemplate(String template, int offset, int limit);
    public PhoneDaoAnswer findAllAndSort(String sortParam, String gradation, int offset, int limit);
    public PhoneDaoAnswer findByTemplateAndSort(String template, String sortParam, String gradation, int offset, int limit);
}
