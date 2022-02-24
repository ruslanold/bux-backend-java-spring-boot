package com.project.myproject.service;

import com.project.myproject.entity.User;

import java.util.Locale;

public interface IEmailService {
    void constructResetTokenEmail(String path, Locale locale, String token, User user);
    void send(String to,String subject, String msg);
}
