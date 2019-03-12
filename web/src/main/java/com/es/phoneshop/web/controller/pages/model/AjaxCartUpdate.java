package com.es.phoneshop.web.controller.pages.model;

import java.math.BigDecimal;

public class AjaxCartUpdate {
    private BigDecimal total;
    private String quantityError;

    public AjaxCartUpdate() {
    }

    public AjaxCartUpdate(BigDecimal total, String quantityError) {
        this.total = total;
        this.quantityError = quantityError;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }
}