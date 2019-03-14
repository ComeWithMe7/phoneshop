package com.es.core.model.phone.service;

import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private PhoneDao phoneDao;

    public ProductListAttributes findProducts(String sortParam, String gradation, String searchLine, int offset, int limit) {
        if (!searchLine.isEmpty() && !sortParam.isEmpty() && !gradation.isEmpty()) {
            return phoneDao.findByTemplateAndSort(searchLine, sortParam, gradation, offset, limit);
        } else if (!searchLine.isEmpty()) {
            return phoneDao.findByTemplate(searchLine, offset, limit);
        } else if (!sortParam.isEmpty() && !gradation.isEmpty()) {
            return phoneDao.findAllAndSort(sortParam, gradation, offset, limit);
        } else {
            return phoneDao.findAll(offset, limit);
        }
    }

    @Override
    public ProductListAttributes setPages(ProductListAttributes productListAttributes, Integer pageNumber, int limitQuantity, int limitPages) {
        if (Math.ceil((double) productListAttributes.getPhonesQuantity() / limitQuantity) > (Math.floor(pageNumber.doubleValue() / limitPages) + 1) * limitPages) {
            productListAttributes.setPageLimit((int) ((Math.floor(pageNumber.doubleValue() / limitPages) + 1) * limitPages));
            productListAttributes.setStartPage((int) ((double) productListAttributes.getPageLimit() / limitPages - 1) * 10 + 1);
        } else {
            productListAttributes.setPageLimit((int) Math.ceil((double) productListAttributes.getPhonesQuantity() / limitQuantity));
            productListAttributes.setStartPage((int) (Math.floor(pageNumber.doubleValue() / limitPages) * limitPages + 1));
        }
        productListAttributes.setFinalPage((int) Math.ceil((double) productListAttributes.getPhonesQuantity() / limitQuantity));
        return productListAttributes;
    }
}
