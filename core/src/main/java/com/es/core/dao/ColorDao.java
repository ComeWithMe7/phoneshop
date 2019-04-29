package com.es.core.dao;

import com.es.core.model.phone.Color;

import java.util.Set;

public interface ColorDao {
    Set<Color> get(Long phoneKey);
    void save(Color color);
    void save(Set<Color> colors, Long phoneKey);
}
