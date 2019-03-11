package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

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
        cart.add(new CartItem(phoneId, quantity));
        System.out.println(phoneId);
        Optional<Phone> phone = phoneDao.get(phoneId);
        if (phone.isPresent() && phone.get().getPrice() != null) {
             cart.setTotal(cart.getTotal().add(phone.get().getPrice().multiply(BigDecimal.valueOf(quantity))));
        }
    }

    @Override
    public void addPhone(CartItem cartItem, Cart cart) {
        cart.add(cartItem);
        Optional<Phone> phone = phoneDao.get(cartItem.getId());
        if (phone.isPresent() && phone.get().getPrice() != null) {
            cart.setTotal(cart.getTotal().add(phone.get().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
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
