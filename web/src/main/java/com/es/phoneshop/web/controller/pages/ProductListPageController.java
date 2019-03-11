package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.service.PhoneDaoAnswer;
import com.es.core.model.phone.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {

    private final int QUANTITY_LIMIT = 10;

    private final int PAGE_LIMIT = 10;

    @Resource
    private ProductService productService;

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(value = "sortingParameter", defaultValue = "") String sortParam, @RequestParam(value = "gradation", defaultValue = "") String gradation, @RequestParam(value = "searchLine", defaultValue = "") String searchLine, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, Model model, HttpSession session) {
        PhoneDaoAnswer phoneDaoAnswer = productService.findProducts(sortParam, gradation, searchLine, (pageNumber - 1) * QUANTITY_LIMIT, QUANTITY_LIMIT);
        phoneDaoAnswer = productService.setPages(phoneDaoAnswer, pageNumber, QUANTITY_LIMIT, PAGE_LIMIT);
        model.addAttribute("phones", phoneDaoAnswer.getPhones());
        model.addAttribute("countPhones", phoneDaoAnswer.getPhonesQuantity());
        model.addAttribute("searchLineAttrib", searchLine);
        model.addAttribute("sortParam", sortParam);
        model.addAttribute("gradation", gradation);
        model.addAttribute("pageLimit", phoneDaoAnswer.getPageLimit());
        model.addAttribute("startPage", phoneDaoAnswer.getStartPage());
        model.addAttribute("finalPage", phoneDaoAnswer.getFinalPage());
        model.addAttribute("cartTotal", cartService.getCart().getTotal());
        return "productList";
    }
}
