package com.digi.auth.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{6,15}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.isBlank()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(value).matches();
    }
}


