package com.project.myproject.converter;

import com.project.myproject.enums.ECountry;

import javax.persistence.Converter;

//@Converter(autoApply = true)
public class CountryNameConverter extends EnumDbValueConverter<ECountry, String> {
    public CountryNameConverter() {
        super(ECountry.class);
    }
}
