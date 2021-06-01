package com.project.myproject.converter;

import com.project.myproject.enums.ERole;
import com.project.myproject.enums.EStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusNameConverter extends EnumDbValueConverter<EStatus, String>{
    public StatusNameConverter() {
        super(EStatus.class);
    }
}
