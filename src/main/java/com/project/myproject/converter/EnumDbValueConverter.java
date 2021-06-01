package com.project.myproject.converter;

import com.project.myproject.enums.IDbvalue;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;

@Slf4j
public abstract class EnumDbValueConverter <E extends Enum<E> & IDbvalue<T>, T extends java.io.Serializable> implements AttributeConverter<E, T> {

    private final Class<E> clazz;

    public EnumDbValueConverter(Class<E> clazz){
        this.clazz = clazz;
    }

    @Override
    public T convertToDatabaseColumn(E attribute) {
        if(attribute == null){
            return null;
        }
        return attribute.getDbValue();
    }

    @Override
    public E convertToEntityAttribute(T dbColumn) {
        if (dbColumn == null){
            return null;
        }

        for (E e : clazz.getEnumConstants()) {
            if (dbColumn.equals(e.getDbValue())) {
                return e;
            }
        }

        log.error("Unable to convert {} to enum {}.", dbColumn, clazz.getCanonicalName());
        return null;
    }
}
