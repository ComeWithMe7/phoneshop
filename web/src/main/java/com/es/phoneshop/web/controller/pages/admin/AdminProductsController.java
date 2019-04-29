package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.service.ProductListAttributes;
import com.es.core.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/phones")
public class AdminProductsController {

    private final int QUANTITY_LIMIT = 10;

    private final int PAGE_LIMIT = 9;

    @Resource
    private ProductService productService;
    @GetMapping
    public String getProducts(@RequestParam(value = "sortingParameter", defaultValue = "") String sortParam, @RequestParam(value = "gradation", defaultValue = "") String gradation, @RequestParam(value = "searchLine", defaultValue = "") String searchLine, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, Model model) {
        ProductListAttributes productListAttributes = productService.findProducts(sortParam, gradation, searchLine, (pageNumber - 1) * QUANTITY_LIMIT, QUANTITY_LIMIT);
        productListAttributes = productService.setPages(productListAttributes, pageNumber, QUANTITY_LIMIT, PAGE_LIMIT);
        model.addAttribute("phones", productListAttributes.getPhones());
        model.addAttribute("countPhones", productListAttributes.getPhonesQuantity());
        model.addAttribute("searchLineAttrib", searchLine);
        model.addAttribute("sortParam", sortParam);
        model.addAttribute("gradation", gradation);
        model.addAttribute("pageLimit", productListAttributes.getPageLimit());
        model.addAttribute("startPage", productListAttributes.getStartPage());
        model.addAttribute("finalPage", productListAttributes.getFinalPage());
        model.addAttribute("currentPage", productListAttributes.getCurrentPage());
        return "adminProducts";
    }
}
