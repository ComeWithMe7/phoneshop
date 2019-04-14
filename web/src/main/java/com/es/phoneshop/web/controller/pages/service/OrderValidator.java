package com.es.phoneshop.web.controller.pages.service;

import com.es.core.cart.CartService;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.pages.model.CartItemUpdate;
import com.es.phoneshop.web.controller.pages.model.OrderView;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderValidator implements Validator {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private CartService cartService;

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderView.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderView orderView = (OrderView) o;
        List<CartItemUpdate> cartItemUpdates = orderView.getCartItemUpdates();
        for (int i = 0; i < cartItemUpdates.size(); i++) {
            CartItemUpdate cartItemUpdate = cartItemUpdates.get(i);
            Long stock = phoneDao.getStock(cartItemUpdate.getId());
            if (stock < cartItemUpdate.getQuantity()) {
                cartService.update(cartItemUpdate.getId(), stock);
                cartItemUpdate.setQuantity(stock);
                errors.rejectValue("cartItemUpdates[" + i + "].quantity", "out.of.stock");
            }
        }
    }
}
