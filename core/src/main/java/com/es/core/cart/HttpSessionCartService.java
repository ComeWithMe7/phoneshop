package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private Cart cart;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        List<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> cartItem = cartItems.stream()
                .filter(x -> phoneId.equals(x.getPhone().getId()))
                .findAny();
        if (cartItem.isPresent() && cartItem.get().getPhone().getPrice() != null) {
            CartItem existingCartItem = cartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            Optional<Phone> phone = phoneDao.get(phoneId);
            if (phone.isPresent() && phone.get().getPrice() != null) {
                cartItems.add(new CartItem(phone.get(), quantity));
            }
        }
        countTotal();
        countProducts();
    }

    @Override
    public void update(Map<Long, Long> cartItems) {
        List<CartItem> newCartItems = new ArrayList<>();
        List<CartItem> cartCartItems = cart.getCartItems();
        for (Map.Entry<Long, Long> cartItem : cartItems.entrySet()) {
            Optional<CartItem> optionalCartItem = cartCartItems.stream()
                    .filter(x -> x.getPhone().getId().equals(cartItem.getKey()))
                    .findAny();
            optionalCartItem.ifPresent(cartItem1 -> newCartItems.add(new CartItem(cartItem1.getPhone(), cartItem.getValue())));
        }
        cart.setCartItems(newCartItems);
        countTotal();
        countProducts();
    }

    @Override
    public void update(Long phoneId, Long quantity) {
        Optional<CartItem> cartItem = cart.getCartItems().stream()
                .filter(x -> x.getPhone().getId().equals(phoneId))
                .findAny();
        cartItem.ifPresent(cartItem1 -> cartItem1.setQuantity(quantity));
        countTotal();
        countProducts();
    }

    @Override
    public void remove(Long phoneId) {
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.removeIf(x -> x.getPhone().getId().equals(phoneId));
        countTotal();
        countProducts();
    }

    @Override
    public void cleanCart() {
        cart.setTotal(BigDecimal.ZERO);
        cart.setCartItems(new ArrayList<>());
        cart.setProductsNumber(0L);
    }

    private void countProducts() {
        cart.setProductsNumber(cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum());
    }

    private void countTotal() {
        List<CartItem> cartItems = cart.getCartItems();
        BigDecimal total = cartItems.stream()
                .reduce(BigDecimal.ZERO,
                        (x, y) -> x.add(y.getPhone().getPrice().multiply(BigDecimal.valueOf(y.getQuantity()))),
                        BigDecimal::add);
        cart.setTotal(total);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
