package com.es.core.model.phone;

import java.util.Set;

public interface ColorDao {
    Set<Color> get(Long phoneKey);
    void save(Color color);
    void save(Set<Color> colors, Long phoneKey);
}
