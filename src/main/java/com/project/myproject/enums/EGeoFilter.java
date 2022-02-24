package com.project.myproject.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum EGeoFilter implements IDbvalue<Integer>{

    ALL(0),
    ONLY(1),
    FORBIDDEN(2);


    private int value;

    static Map<Integer, EGeoFilter> values = Arrays.stream(EGeoFilter.values())
            .collect(Collectors.toMap(EGeoFilter::getDbValue, Function.identity()));

    EGeoFilter(int value){
        this.value = value;
    }

    @Override
    public Integer getDbValue() {
        return value;
    }

    public static EGeoFilter fromValue(int value){
        return values.get(value);
    }
}
