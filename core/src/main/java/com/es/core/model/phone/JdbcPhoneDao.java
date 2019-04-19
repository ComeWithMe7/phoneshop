package com.es.core.model.phone;

import com.es.core.model.phone.service.ProductListAttributes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class JdbcPhoneDao implements PhoneDao{

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private ColorDao colorDao;

    private String UP_SORT = "up";

    private String DOWN_SORT = "down";


    private String SQL_GET_BY_ID = "select * from phones where id = :id";

    private String SQL_SAVE_PHONE = "insert into phones (brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm," +
            "announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels," +
            "ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) " +
            "values (:brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, :widthMm, :heightMm," +
            ":announced, :deviceType, :os, :displayResolution, :pixelDensity, :displayTechnology, :backCameraMegapixels, :frontCameraMegapixels," +
            ":ramGb, :internalStorageGb, :batteryCapacityMah, :talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";

    private String SQL_FIND_TEMPLATE = "select * from phones join stocks on phones.id = stocks.phoneId where stock > 0";

    private String SQL_SELECT_COUNT_TEMPLATE = "select count(*) from phones join stocks on phones.id = stocks.phoneId where stock > 0";

    private String SQL_GET_STOCK = "select stock from stocks where phoneId = :phoneId";

    private String SQL_UPDATE_STOCK = "update stocks set stock = :stock where phoneId = :phoneId";

    private Map<String, String> sortParams;

    public JdbcPhoneDao() {
        sortParams = new HashMap<>(3);
        sortParams.put("brand", "brand");
        sortParams.put("model", "brand");
        sortParams.put("displaySize", "displaySizeInches");
    }

    public Optional<Phone> get(final Long key) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", key);
        try {
            Phone phone = (Phone) namedParameterJdbcTemplate.queryForObject(SQL_GET_BY_ID, namedParameters, new BeanPropertyRowMapper(Phone.class));
            phone.setColors(colorDao.get(key));
            return Optional.of(phone);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    public void save(final Phone phone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phone);
        int status = namedParameterJdbcTemplate.update(SQL_SAVE_PHONE, namedParameters, keyHolder);
        if (status != 0) {
            phone.setId(keyHolder.getKey().longValue());
            colorDao.save(phone.getColors(), keyHolder.getKey().longValue());
        }
    }

    public ProductListAttributes findAll(int offset, int limit) {
        List<Phone> phones = namedParameterJdbcTemplate.query(SQL_FIND_TEMPLATE + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
        return setProperties(phones);
    }

    public ProductListAttributes findByTemplate(String template, int offset, int limit) {
        template = template.toLowerCase();
        List<Phone> phones = namedParameterJdbcTemplate.query(SQL_FIND_TEMPLATE + " and LOWER (model) like '%" + template + "%' offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
        return setPropertiesWithTemplate(phones, template);
    }

    public ProductListAttributes findAllAndSort(String sortParam, String gradation, int offset, int limit) {
        sortParam = sortParams.get(sortParam);
        if (DOWN_SORT.equals(gradation) && sortParam != null) {
            List<Phone> phones = namedParameterJdbcTemplate.query(SQL_FIND_TEMPLATE + " order by " + sortParam + " desc" + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
            return setProperties(phones);
        } else if (UP_SORT.equals(gradation) && sortParam != null) {
            List<Phone> phones = namedParameterJdbcTemplate.query(SQL_FIND_TEMPLATE + " order by " + sortParam + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
            return setProperties(phones);
        } else {
            return findAll(offset, limit);
        }
    }

    public ProductListAttributes findByTemplateAndSort(String template, String sortParam, String gradation, int offset, int limit) {
        sortParam = sortParams.get(sortParam);
        template = template.toLowerCase();
        if (DOWN_SORT.equals(gradation) && sortParam != null) {
            List<Phone> phones = namedParameterJdbcTemplate.query(SQL_FIND_TEMPLATE + " and LOWER (model) like '%" + template + "%' order by " + sortParam + " desc" + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
            return setPropertiesWithTemplate(phones, template);
        } else if (UP_SORT.equals(gradation) && sortParam != null) {
            List<Phone> phones = namedParameterJdbcTemplate.query(SQL_FIND_TEMPLATE + " and LOWER (model) like '%" + template + "%' order by " + sortParam + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper<>(Phone.class));
            return setPropertiesWithTemplate(phones, template);
        } else {
            return findAll(offset, limit);
        }
    }

    @Override
    public Long getStock(final Long key) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("phoneId", key);
        return namedParameterJdbcTemplate.queryForObject(SQL_GET_STOCK, namedParameters, Long.class);
    }

    @Override
    public void updateStock(Map<Long, Long> updatedStock) {
        MapSqlParameterSource[] mapSqlParameterSources = updatedStock.entrySet().stream().map(x -> {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("phoneId", x.getKey());
            parameterSource.addValue("stock", x.getValue());
            return parameterSource;
        }).toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(SQL_UPDATE_STOCK, mapSqlParameterSources);
    }

    private Integer selectCount() {
        return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(SQL_SELECT_COUNT_TEMPLATE, Integer.class);
    }

    private Integer selectCountByTemplate(String template) {
        template = template.toLowerCase();
        return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(SQL_SELECT_COUNT_TEMPLATE + " and LOWER (model) like '%" + template + "%'", Integer.class);
    }

    private ProductListAttributes setProperties(List<Phone> phones) {
        ProductListAttributes productListAttributes = new ProductListAttributes();
        for (Phone phone : phones) {
            phone.setColors(colorDao.get(phone.getId()));
        }
        productListAttributes.setPhones(phones);
        productListAttributes.setPhonesQuantity(selectCount());
        return productListAttributes;
    }

    private ProductListAttributes setPropertiesWithTemplate(List<Phone> phones, String template) {
        ProductListAttributes productListAttributes = new ProductListAttributes();
        for (Phone phone : phones) {
            phone.setColors(colorDao.get(phone.getId()));
        }
        productListAttributes.setPhones(phones);
        productListAttributes.setPhonesQuantity(selectCountByTemplate(template));
        return productListAttributes;
    }
}