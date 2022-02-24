package com.project.myproject.enums;

public enum EModerationStatus implements IDbvalue<String>{
    DRAFT("DRAFT"),
    PENDING("PENDING"),
    REWORKING("REWORKING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private String name;

    EModerationStatus(String name) {
        this.name = name;
    }

    @Override
    public String getDbValue() {
        return name;
    }
}
