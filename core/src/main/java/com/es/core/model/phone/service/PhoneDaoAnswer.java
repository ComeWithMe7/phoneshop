package com.es.core.model.phone.service;

import com.es.core.model.phone.Phone;

import java.util.List;

public class PhoneDaoAnswer {

    private List<Phone> phones;

    private Integer phonesQuantity;

    private Integer pageLimit;

    private Integer startPage;

    private Integer finalPage;

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Integer getPhonesQuantity() {
        return phonesQuantity;
    }

    public void setPhonesQuantity(Integer phonesQuantity) {
        this.phonesQuantity = phonesQuantity;
    }

    public Integer getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(Integer pageLimit) {
        this.pageLimit = pageLimit;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getFinalPage() {
        return finalPage;
    }

    public void setFinalPage(Integer finalPage) {
        this.finalPage = finalPage;
    }
}
