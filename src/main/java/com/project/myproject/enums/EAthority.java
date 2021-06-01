package com.project.myproject.enums;

public enum EAthority implements IDbvalue<String>{

    READ_PROFILE("READ_PROFILE"),
    EDIT_PROFILE("EDIT_PROFILE"),
    UPLOAD_USER_IMG("UPLOAD_USER_IMG");


    private String name;

    EAthority(String name) {
        this.name = name;
    }

    @Override
    public String getDbValue() {
        return name;
    }
}
