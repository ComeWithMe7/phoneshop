package com.es.core.model.phone.service;

import com.es.core.model.phone.Phone;

import java.util.Comparator;

import static java.util.Comparator.comparing;

public enum SortOption {

    BRAND_ASC(comparing(Phone::getBrand)),
    BRAND_DESC(comparing(Phone::getBrand).reversed()),
    MODEL_ASC(comparing(Phone::getModel)),
    MODEL_DESC(comparing(Phone::getModel).reversed());

    private final Comparator<Phone> comparator;

    SortOption(Comparator<Phone> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Phone> getComparator() {
        return comparator;
    }

    public static SortOption from(String sortParam) {
        switch (sortParam) {
            case "upBrand":
                return BRAND_ASC;
            case "downBrand":
                return BRAND_DESC;
            case "upModel":
                return MODEL_ASC;
            case "downModel":
                return MODEL_DESC;
        }
        throw new IllegalArgumentException("No enum constant for sort param: " + sortParam);
    }
}