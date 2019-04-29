package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.core.exceptions.ProductNotFoundException;
import com.es.core.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("count", cartService.getCart().getProductsNumber());
        return "productDetails";
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND,
            reason="Product with such id wasn't founded")
    @ExceptionHandler(ProductNotFoundException.class)
    public void productNotFound() {

    }
}
