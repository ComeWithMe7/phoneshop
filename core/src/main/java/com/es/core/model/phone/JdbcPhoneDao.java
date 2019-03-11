package com.es.core.model.phone;

import com.es.core.model.phone.service.PhoneDaoAnswer;
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

    public PhoneDaoAnswer findAll(int offset, int limit) {
        PhoneDaoAnswer phoneDaoAnswer = new PhoneDaoAnswer();
        List<Phone> phones = (List<Phone>) namedParameterJdbcTemplate.query("select * from phones join stocks on phones.id = stocks.phoneId where stock > 0  offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
        for (Phone phone : phones) {
            phone.setColors(colorDao.get(phone.getId()));
        }
        phoneDaoAnswer.setPhones(phones);
        phoneDaoAnswer.setPhonesQuantity(namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phones join stocks on phones.id = stocks.phoneId where stock > 0", Integer.class));
        return phoneDaoAnswer;
    }

    public PhoneDaoAnswer findByTemplate(String template, int offset, int limit) {
        PhoneDaoAnswer phoneDaoAnswer = new PhoneDaoAnswer();
        template = template.toLowerCase();
        List<Phone> phones = (List<Phone>) namedParameterJdbcTemplate.query("select * from phones join stocks on phones.id = stocks.phoneId where stock > 0 and LOWER (model) like '%" + template + "%' offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
        for (Phone phone : phones) {
            phone.setColors(colorDao.get(phone.getId()));
        }
        phoneDaoAnswer.setPhones(phones);
        phoneDaoAnswer.setPhonesQuantity(namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phones join stocks on phones.id = stocks.phoneId where stock > 0 and LOWER (model) like '%" + template + "%'", Integer.class));
        return phoneDaoAnswer;
    }

    public PhoneDaoAnswer findAllAndSort(String sortParam, String gradation, int offset, int limit) {
        PhoneDaoAnswer phoneDaoAnswer = new PhoneDaoAnswer();
        sortParam = sortParams.get(sortParam);
        if (DOWN_SORT.equals(gradation) && sortParam != null) {
            List<Phone> phones = (List<Phone>) namedParameterJdbcTemplate.query("select * from phones join stocks on phones.id = stocks.phoneId where stock > 0 order by " + sortParam + " desc" + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
            for (Phone phone : phones) {
                phone.setColors(colorDao.get(phone.getId()));
            }
            phoneDaoAnswer.setPhones(phones);
            phoneDaoAnswer.setPhonesQuantity(namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phones join stocks on phones.id = stocks.phoneId where stock > 0", Integer.class));
            return phoneDaoAnswer;
        } else if (UP_SORT.equals(gradation) && sortParam != null) {
            List<Phone> phones = (List<Phone>) namedParameterJdbcTemplate.query("select * from phones join stocks on phones.id = stocks.phoneId where stock > 0 order by " + sortParam + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
            for (Phone phone : phones) {
                phone.setColors(colorDao.get(phone.getId()));
            }
            phoneDaoAnswer.setPhones(phones);
            phoneDaoAnswer.setPhonesQuantity(namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phones join stocks on phones.id = stocks.phoneId where stock > 0", Integer.class));
            return phoneDaoAnswer;
        } else {
            return findAll(offset, limit);
        }
    }

    public PhoneDaoAnswer findByTemplateAndSort(String template, String sortParam, String gradation, int offset, int limit) {
        PhoneDaoAnswer phoneDaoAnswer = new PhoneDaoAnswer();
        sortParam = sortParams.get(sortParam);
        template = template.toLowerCase();
        if (DOWN_SORT.equals(gradation) && sortParam != null) {
            List<Phone> phones = (List<Phone>) namedParameterJdbcTemplate.query("select * from phones where LOWER (model) like '%" + template + "%' order by " + sortParam + " desc" + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
            for (Phone phone : phones) {
                phone.setColors(colorDao.get(phone.getId()));
            }
            phoneDaoAnswer.setPhones(phones);
            phoneDaoAnswer.setPhonesQuantity(namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phones join stocks on phones.id = stocks.phoneId where stock > 0 and LOWER (model) like '%" + template + "%'", Integer.class));
            return phoneDaoAnswer;
        } else if (UP_SORT.equals(gradation) && sortParam != null) {
            List<Phone> phones = (List<Phone>) namedParameterJdbcTemplate.query("select * from phones where LOWER (model) like '%" + template + "%' order by " + sortParam + " offset " + offset + " limit " + limit, new BeanPropertyRowMapper(Phone.class));
            for (Phone phone : phones) {
                phone.setColors(colorDao.get(phone.getId()));
            }
            phoneDaoAnswer.setPhones(phones);
            phoneDaoAnswer.setPhonesQuantity(namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phones join stocks on phones.id = stocks.phoneId where stock > 0 and LOWER (model) like '%" + template + "%'", Integer.class));
            return phoneDaoAnswer;
        } else {
            return findAll(offset, limit);
        }
    }
}
