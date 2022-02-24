package com.project.myproject.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTest {

    @Mock
    private ValidPassword validPassword;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void invalidPassword(){
        //given
        PasswordValidator passwordValidator = new PasswordValidator();
        passwordValidator.initialize(validPassword);


        //when
        boolean empty = passwordValidator.isValid(" ", constraintValidatorContext);
        boolean empty1 = passwordValidator.isValid("", constraintValidatorContext);
        boolean shortPass = passwordValidator.isValid("asdasdf", constraintValidatorContext);
        boolean longPass = passwordValidator.isValid("asdfghjkloiuytredfvcx", constraintValidatorContext);
        boolean space = passwordValidator.isValid(" aasdfgh", constraintValidatorContext);
        boolean space1 = passwordValidator.isValid("asdfghd ", constraintValidatorContext);
        boolean space3 = passwordValidator.isValid("saa sdfg", constraintValidatorContext);

        //then
        assertFalse(empty, "invalid, empty");
        assertFalse(empty1, "invalid, empty");
        assertFalse(shortPass, "invalid, short password");
        assertFalse(longPass, "invalid, long password");
        assertFalse(space, "invalid, password contains spaces");
        assertFalse(space1, "invalid, password contains spaces");
        assertFalse(space3, "invalid, password contains spaces");

    }



    @Test
    void validPassword(){
        //given
        PasswordValidator passwordValidator = new PasswordValidator();
        passwordValidator.initialize(validPassword);

        //when
        boolean length = passwordValidator.isValid("0123456789$abcdefgAB",constraintValidatorContext);
        boolean length1 = passwordValidator.isValid("123Aa$Aa",constraintValidatorContext);


        //then
        assertTrue(length, "valid, 20 characters");
        assertTrue(length1, "valid, 8 characters");

    }

}
