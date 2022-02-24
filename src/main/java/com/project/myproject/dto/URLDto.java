package com.project.myproject.dto;

import org.hibernate.validator.constraints.URL;

public class URLDto {

    @URL
    private String address;

}
