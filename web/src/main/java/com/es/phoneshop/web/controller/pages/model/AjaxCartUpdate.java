package com.es.phoneshop.web.controller.pages.model;

import com.es.core.cart.Cart;

import java.math.BigDecimal;

public class AjaxCartUpdate {

    private BigDecimal total;

    private String quantityError;

    private Long count;

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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static AjaxCartUpdate createAjaxCartUpdate(Cart cart) {
        AjaxCartUpdate ajaxResponse = new AjaxCartUpdate();
        ajaxResponse.setTotal(cart.getTotal());
        ajaxResponse.setCount(cart.getProductsNumber());
        return ajaxResponse;
    }

    public static AjaxCartUpdate createAjaxCartUpdateWithError(Cart cart, String errorMessage) {
        AjaxCartUpdate ajaxResponse = createAjaxCartUpdate(cart);
        ajaxResponse.setQuantityError(errorMessage);
        return ajaxResponse;
    }
}
