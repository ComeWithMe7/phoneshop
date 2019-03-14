package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
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
    public void addPhone(Long phoneId, Long quantity, Cart cart) {
        Set<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> cartItem = cartItems.stream()
                .filter(x -> phoneId.equals(x.getPhone().getId()))
                .findAny();
        if (cartItem.isPresent() && cartItem.get().getPhone().getPrice() != null) {
            CartItem existingCartItem = cartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cart.setTotal(cart.getTotal().add(existingCartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(quantity))));
        } else {
            Optional<Phone> phone = phoneDao.get(phoneId);
            if (phone.isPresent() && phone.get().getPrice() != null) {
                cartItems.add(new CartItem(phone.get(), quantity));
                cart.setTotal(cart.getTotal().add(phone.get().getPrice().multiply(BigDecimal.valueOf(quantity))));
            }
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }
}
