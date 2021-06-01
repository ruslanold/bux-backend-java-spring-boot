package com.project.myproject.enums;

public enum EStatus implements IDbvalue<String>{
    PENDING("PENDING"),
    REWORKING("REWORKING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private String name;

    EStatus(String name) {
        this.name = name;
    }

    @Override
    public String getDbValue() {
        return name;
    }
}
