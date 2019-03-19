package com.es.phoneshop.web.controller.pages.model;

import com.es.core.model.phone.Phone;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemUpdate {

    private Long id;

    @NotNull
    @Min(1)
    private Long quantity;

    private Phone phone;

    public CartItemUpdate() {
    }

    public CartItemUpdate(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public CartItemUpdate(Long quantity, Phone phone) {
        this.quantity = quantity;
        this.phone = phone;
    }

    public CartItemUpdate(Long id, Long quantity, Phone phone) {
        this.id = id;
        this.quantity = quantity;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
