package com.project.myproject.controller;

import com.project.myproject.dto.BadRequestException;
import com.project.myproject.dto.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorController {


    @ExceptionHandler(value = BadRequestException.class)
    public ResponseErrorDto handleBadRequestException(BadRequestException ex) {
        return new ResponseErrorDto(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }


}
