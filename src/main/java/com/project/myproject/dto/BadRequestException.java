package com.project.myproject.dto;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }

}
