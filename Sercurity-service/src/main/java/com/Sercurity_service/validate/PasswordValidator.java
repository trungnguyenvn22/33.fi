package com.Sercurity_service.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Objects;


public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    int minLength;
    boolean requireUppercase;
    boolean requireLowercase;
    boolean requireSpecialChar;
    boolean requireNumber;


    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.requireUppercase = constraintAnnotation.requireUppercase();
        this.requireLowercase = constraintAnnotation.requireLowercase();
        this.requireSpecialChar = constraintAnnotation.requireSpecialChar();
        this.requireNumber = constraintAnnotation.requireNumber();


    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(password == null){
            return false;
        }

        boolean isValid = true;
        StringBuilder message = new StringBuilder();

        if (password.length() < minLength) {
            isValid = false;
            message.append("Password must be at least "+ minLength + "characters long");

        }
        if(requireUppercase && !password.matches(".*[A-Z].*")){
            isValid = false;
            message.append("Password must contain uppercase character");
        }
        if(requireLowercase && !password.matches(".*[a-z].*")){
            isValid = false;
            message.append("Password must contain lowercase character");
        }
        if(requireNumber && !password.matches(".*[0-9].*")){
            isValid = false;
            message.append("Password must contain number character");
        }
        if(requireSpecialChar && !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")){
            isValid = false;
            message.append("Password must contain special character");
        }

        if(!isValid){
            context.buildConstraintViolationWithTemplate(message.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
