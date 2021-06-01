package com.project.myproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN = "^\\S{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
       Matcher matcher = pattern.matcher(password);
       return matcher.matches();
    }
}
