package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        Set<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> cartItem = cartItems.stream()
                .filter(x -> phoneId.equals(x.getPhone().getId()))
                .findAny();
        if (cartItem.isPresent() && cartItem.get().getPhone().getPrice() != null) {
            CartItem existingCartItem = cartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            countTotal();
        } else {
            Optional<Phone> phone = phoneDao.get(phoneId);
            if (phone.isPresent() && phone.get().getPrice() != null) {
                cartItems.add(new CartItem(phone.get(), quantity));
                countTotal();
            }
        }
    }

    @Override
    public void update(Set<CartItem> cartItems) {
        Set<CartItem> newCartItems = new HashSet<>();
        Set<CartItem> cartCartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            Optional<CartItem> optionalCartItem = cartCartItems.stream()
                    .filter(x -> x.getPhone().getId().equals(cartItem.getPhone().getId()))
                    .findAny();
            optionalCartItem.ifPresent(cartItem1 -> newCartItems.add(new CartItem(cartItem1.getPhone(), cartItem.getQuantity())));
        }
        cart.setCartItems(newCartItems);
        countTotal();
    }

    @Override
    public void remove(Long phoneId) {
        Set<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> cartItem = cartItems.stream()
                .filter(x -> phoneId.equals(x.getPhone().getId()))
                .findAny();
        if (cartItem.isPresent()) {
            cartItems.removeIf(x -> x.getPhone().getId().equals(phoneId));
            countTotal();
        }
    }

    public void countTotal() {
        Set<CartItem> cartItems = cart.getCartItems();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getPhone().getPrice() != null) {
                total = total.add(cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            }
        }
        cart.setTotal(total);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
