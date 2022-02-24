package com.project.myproject.enums;

public enum EWeek {

    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(4),
    THURSDAY(8),
    FRIDAY(16),
    SATURDAY(32),
    SUNDAY(64);

    private int value;

    EWeek(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
