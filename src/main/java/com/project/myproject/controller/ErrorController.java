package com.project.myproject.controller;

import com.project.myproject.dto.BadRequestException;
import com.project.myproject.dto.EntityNotFoundException;
import com.project.myproject.dto.ResponseErrorDto;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = AuthenticationException.class) //BadCredentialsException, LockedException, DisabledException
    public ResponseEntity<ResponseErrorDto> handleAuthenticationException(AuthenticationException ex) {
        ResponseErrorDto error = new ResponseErrorDto(LocalDateTime.now(), ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> msgs = ex.getBindingResult().getFieldErrors().stream().map(error -> {
            return error.getField() + ": " + error.getDefaultMessage();
        }).collect(Collectors.toList());
        ResponseErrorDto error = new ResponseErrorDto(LocalDateTime.now(), msgs.toString(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseErrorDto> handleBadRequestException(BadRequestException ex) {
        ResponseErrorDto error = new ResponseErrorDto(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> handlerEntityNotFoundException(EntityNotFoundException ex){

        ResponseErrorDto error = new ResponseErrorDto(LocalDateTime.now(), ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }








}
