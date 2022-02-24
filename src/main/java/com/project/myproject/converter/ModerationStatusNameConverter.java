package com.project.myproject.converter;

import com.project.myproject.enums.EModerationStatus;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ModerationStatusNameConverter extends EnumDbValueConverter<EModerationStatus, String>{
    public ModerationStatusNameConverter() {
        super(EModerationStatus.class);
    }
}
