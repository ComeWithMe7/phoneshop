package com.es.core.model.phone;

import com.es.core.model.phone.service.ProductListAttributes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class JdbcPhoneDaoTest {

    @Resource
    private PhoneDao jdbcPhoneDao;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private int offset = 0;

    private int limit = 10;


    @Test
    public void getWithCorrectIdTest() {
        final long correctId = 1001;
        Phone phone = jdbcPhoneDao.get(correctId).get();
        assertNotNull(phone);
        assertThat(phone.getId(), is(correctId));
    }

    @Test
    public void getWithInvalidIdTest() {
        final long invalidId = -2;
        assertThat(jdbcPhoneDao.get(invalidId).isPresent(), is(false));
    }

    @Rollback(true)
    @Test
    public void saveTest() {
        int tableSizeBeforeInsert = getCount();
        jdbcPhoneDao.save(createPhone());
        int tableSizeAfterInsert = getCount();
        assertThat(tableSizeAfterInsert, is(tableSizeBeforeInsert + 1));
    }

    @Test
    public void findAllTest() {
        ProductListAttributes productListAttributes = jdbcPhoneDao.findAll(offset, limit);
        assertThat(productListAttributes.getPhones().size(), is(limit - offset));
        assertNotNull(productListAttributes.getPhonesQuantity());
    }

    @Test
    public void findByTemplateTest() {
        ProductListAttributes productListAttributes1 = jdbcPhoneDao.findByTemplate("ACER", offset, limit);
        ProductListAttributes productListAttributes2 = jdbcPhoneDao.findByTemplate("acer", offset, limit);
        assertEquals(productListAttributes1.getPhonesQuantity(), productListAttributes2.getPhonesQuantity());
        assertNotNull(productListAttributes1.getPhones());
        assertNotNull(productListAttributes2.getPhones());
    }

    @Test
    public void findAllAndSort() {
        ProductListAttributes productListAttributes1 = jdbcPhoneDao.findAllAndSort("brand", "up", offset, limit);
        ProductListAttributes productListAttributes2 = jdbcPhoneDao.findAllAndSort("brand", "down", offset, limit);
        ProductListAttributes productListAttributes3 = jdbcPhoneDao.findAll(offset, limit);
        assertEquals(productListAttributes1.getPhonesQuantity(), productListAttributes2.getPhonesQuantity());
        assertEquals(productListAttributes1.getPhones().size(), productListAttributes2.getPhones().size());
        assertEquals(productListAttributes3.getPhonesQuantity(), productListAttributes1.getPhonesQuantity());
    }

    @Test
    public void findByTemplateAndSort() {
        ProductListAttributes productListAttributes1 = jdbcPhoneDao.findByTemplateAndSort("aser", "brand", "up", offset, limit);
        ProductListAttributes productListAttributes2 = jdbcPhoneDao.findByTemplate("ASER", offset, limit);
        assertEquals(productListAttributes1.getPhonesQuantity(), productListAttributes2.getPhonesQuantity());
    }

    public int getCount() {
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phones", Integer.class);
    }

    public Phone createPhone() {
        Phone phone = new Phone();
        phone.setBrand("as");
        phone.setModel("qw");
        return phone;
    }
}