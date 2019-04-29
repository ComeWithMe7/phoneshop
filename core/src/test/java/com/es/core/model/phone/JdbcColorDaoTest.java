package com.es.core.model.phone;

import com.es.core.dao.ColorDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class JdbcColorDaoTest {

    @Resource
    private ColorDao colorDao;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void getColorSetByPhoneIdTest() {
        final long phoneId = 1001;
        Set<Color> colors = colorDao.get(phoneId);
        assertNotNull(colors);
    }

    @Rollback(true)
    @Test
    public void saveNewColorTest() {
        int tableSizeBeforeInsert = getColorsCount();
        colorDao.save(createNewColor());
        int tableSizeAfterInsert = getColorsCount();
        assertThat(tableSizeAfterInsert, is(tableSizeBeforeInsert + 1));
    }

    @Rollback(true)
    @Test(expected = DataAccessException.class)
    public void saveExistingColorTest() {
        int tableSizeBeforeInsert = getColorsCount();
        colorDao.save(createExistingColor());
        int tableSizeAfterInsert = getColorsCount();
        assertThat(tableSizeAfterInsert, is(tableSizeBeforeInsert));
    }

    public Color createNewColor() {
        Color color =  new Color();
        color.setCode("123457");
        return color;
    }

    public Color createExistingColor() {
        Color color =  new Color();
        color.setCode("White");
        return color;
    }

    public int getColorsCount() {
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from colors", Integer.class);
    }

    public int getPhoneToColorCount() {
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject("select count(*) from phone2color", Integer.class);
    }
}