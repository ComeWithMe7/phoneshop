package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails/{phoneId}")
public class ProductDetailsPageController {

    @Resource
    private CartService cartService;

    @Resource
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductDetails(@PathVariable long phoneId, Model model) {
        model.addAttribute("cartTotal", cartService.getCart().getTotal());
        model.addAttribute("phone", productService.getPhone(phoneId));
        return "productDetails";
    }
}
