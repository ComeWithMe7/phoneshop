package com.es.phoneshop.web.service;

import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.model.ProductView;
import org.springframework.stereotype.Service;

@Service
public class ProductViewService {

    public Phone getPhoneFromView(ProductView productView) {
        Phone phone = new Phone();
        phone.setAnnounced(productView.getAnnounced());
        phone.setBackCameraMegapixels(productView.getBackCameraMegapixels());
        phone.setBatteryCapacityMah(productView.getBatteryCapacityMah());
        phone.setBluetooth(productView.getBluetooth());
        phone.setBrand(productView.getBrand());
        phone.setColors(productView.getColors());
        phone.setDescription(productView.getDescription());
        phone.setDeviceType(productView.getDeviceType());
        phone.setDisplayResolution(productView.getDisplayResolution());
        phone.setDisplaySizeInches(productView.getDisplaySizeInches());
        phone.setDisplayTechnology(productView.getDisplayTechnology());
        phone.setFrontCameraMegapixels(productView.getFrontCameraMegapixels());
        phone.setHeightMm(productView.getHeightMm());
        phone.setId(productView.getId());
        phone.setImageUrl(productView.getImageUrl());
        phone.setInternalStorageGb(productView.getInternalStorageGb());
        phone.setLengthMm(productView.getLengthMm());
        phone.setModel(productView.getModel());
        phone.setOs(productView.getOs());
        phone.setPixelDensity(productView.getPixelDensity());
        phone.setPositioning(productView.getPositioning());
        phone.setPrice(productView.getPrice());
        phone.setRamGb(productView.getRamGb());
        phone.setStandByTimeHours(productView.getStandByTimeHours());
        phone.setTalkTimeHours(productView.getTalkTimeHours());
        phone.setWeightGr(productView.getWeightGr());
        phone.setWidthMm(productView.getWidthMm());
        return phone;
    }
}
