package com.project.myproject.enums;

public enum ERole implements IDbvalue<String>{
    ADMIN("ROLE_PRINCEPS"),
    MODERATOR("ROLE_PRAETOR"),
    USER("ROLE_USER");

    private String name;

    ERole(String name) {
        this.name = name;
    }

    @Override
    public String getDbValue() {
        return name;
    }
}
