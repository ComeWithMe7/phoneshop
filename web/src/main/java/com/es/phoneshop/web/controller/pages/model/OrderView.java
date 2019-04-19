package com.es.phoneshop.web.controller.pages.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class OrderView {

    @Valid
    private List<CartItemUpdate> cartItemUpdates;

    private BigDecimal subtotal;

    private BigDecimal delivery;

    private BigDecimal total;

    @NotEmpty
    @Size(max = 250)
    private String firstName;

    @NotEmpty
    @Size(max = 250)
    private String lastName;

    @NotEmpty
    @Size(max = 250)
    private String address;

    @Pattern(regexp="(\\+[0-9]{12})", message = "Must be in format +375001111111")
    private String phoneNumber;

    @Size(max = 4050)
    private String information;

    public List<CartItemUpdate> getCartItemUpdates() {
        return cartItemUpdates;
    }

    public void setCartItemUpdates(List<CartItemUpdate> cartItemUpdates) {
        this.cartItemUpdates = cartItemUpdates;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDelivery() {
        return delivery;
    }

    public void setDelivery(BigDecimal delivery) {
        this.delivery = delivery;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

}
