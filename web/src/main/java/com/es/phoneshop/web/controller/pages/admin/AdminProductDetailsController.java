package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.exceptions.ProductNotFoundException;
import com.es.core.service.ProductService;
import com.es.phoneshop.web.model.ProductView;
import com.es.phoneshop.web.service.ProductViewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/productDetails/{phoneId}")
public class AdminProductDetailsController {

    @Resource
    private ProductService productService;

    @Resource
    private ProductViewService productViewService;

    @GetMapping
    public String showProductDetails(@PathVariable long phoneId, Model model) {
        model.addAttribute("productView", ProductView.createProductView(productService.getPhone(phoneId)));
        return "adminProductDetails";
    }

    @PutMapping
    public String updateProduct(@Valid ProductView productView, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productView", productView);
            return "adminProductDetails";
        }
        productService.update(productViewService.getPhoneFromView(productView));
        model.addAttribute("phone", ProductView.createProductView(productService.getPhone(productView.getId())));
        return "adminProductDetails";
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND,
            reason="Product with such id wasn't founded")
    @ExceptionHandler(ProductNotFoundException.class)
    public void productNotFound() {

    }
}
