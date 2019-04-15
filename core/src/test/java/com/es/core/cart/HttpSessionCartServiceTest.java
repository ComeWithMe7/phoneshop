package com.es.core.cart;

import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    private HttpSessionCartService cartService;

    private Cart cart;

    private Set<CartItem> cartItems;

    @Mock
    private CartItem cartItem1;

    @Mock
    private CartItem cartItem2;

    @Mock
    private Phone phone1;

    @Mock
    private Phone phone2;

    @Before
    public void setup() {
        when(cartItem1.getPhone()).thenReturn(phone1);
        when(cartItem2.getPhone()).thenReturn(phone2);
        when(cartItem1.getQuantity()).thenReturn(1L);
        when(cartItem2.getQuantity()).thenReturn(2L);
        when(phone1.getPrice()).thenReturn(BigDecimal.valueOf(200));
        when(phone2.getPrice()).thenReturn(BigDecimal.valueOf(100));

        cart = new Cart();
        cartItems = new HashSet<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        cart.setCartItems(cartItems);
        cartService = new HttpSessionCartService();
        cartService.setCart(cart);
    }

    @Test
    public void countTotalTest() {
        cartService.countTotal();
        assertThat(cart.getTotal(), is(BigDecimal.valueOf(400)));
    }
}