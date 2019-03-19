package com.es.core.model.phone.service;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

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
        Integer phonesQuantity = productListAttributes.getPhonesQuantity();
        Integer finalPage = (int) Math.ceil((double) phonesQuantity / limitQuantity);
        productListAttributes.setFinalPage(finalPage);
        if (pageNumber > limitPages / 2) {
            productListAttributes.setStartPage(pageNumber - limitPages / 2);
        } else {
            productListAttributes.setStartPage(1);
        }
        if (pageNumber < finalPage - limitPages / 2) {
            if (pageNumber <= limitPages / 2) {
                productListAttributes.setPageLimit(limitPages);
            }else {
                productListAttributes.setPageLimit(pageNumber + limitPages / 2);
            }
        } else {
            productListAttributes.setPageLimit(finalPage);
        }
        productListAttributes.setCurrentPage(pageNumber);
        return productListAttributes;
    }

    @Override
    public Phone getPhone(long id) {
        Optional<Phone> phone = phoneDao.get(id);
        if (phone.isPresent()) {
            return phone.get();
        } else {
            throw new ProductNotFoundException("Product with id " + id + " wasn't founded");
        }
    }
}
