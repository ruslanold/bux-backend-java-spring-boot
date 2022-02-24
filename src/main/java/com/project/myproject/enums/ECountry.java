package com.project.myproject.enums;

public enum ECountry implements IDbvalue<String>{

    FRANCE("FRANCE"),
    UKRAINE("UKRAINE");

    private String name;

    ECountry(String name) {this.name = name;}


    @Override
    public String getDbValue() {
        return name;
    }
}
