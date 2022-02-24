package com.project.myproject.converter;

import com.project.myproject.enums.EGeoFilter;
import com.project.myproject.enums.ERole;

public class GeoFilterValueConverter extends EnumDbValueConverter<EGeoFilter, Integer>{
    public GeoFilterValueConverter() {
        super(EGeoFilter.class);
    }
}
