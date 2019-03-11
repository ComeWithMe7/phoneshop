package com.es.core.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItem {

    private Long id;

    @NotNull
    @Min(1)
    private Long quantity;

    public CartItem() {
    }

    public CartItem(@NotNull Long id, @Min(0) @NotNull Long quantity) {
        this.id = id;
        this.quantity = quantity;
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
}
