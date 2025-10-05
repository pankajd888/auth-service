package com.digi.auth.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("${app.security.allowed-specials:@#$%_!.}")
    private String allowedSpecials;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        String configuredAllowed = this.allowedSpecials;
        if (configuredAllowed == null) {
            configuredAllowed = "@#$%_!."; // fallback for non-Spring-instantiated validator (e.g., unit tests)
        }
        if (password == null) {
            return false;
        }

        int length = password.length();
        if (length < 8 || length > 32) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            return false;
        }

        String escapedAllowed = escapeForCharacterClass(configuredAllowed);

        if (!password.matches(".*[" + escapedAllowed + "].*")) {
            return false;
        }

        if (!password.matches("^[A-Za-z0-9" + escapedAllowed + "]+$")) {
            return false;
        }

        return true;
    }

    private String escapeForCharacterClass(String specials) {
        if (specials == null || specials.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (char ch : specials.toCharArray()) {
            // Characters that must be escaped inside a character class: \\ ^ - ]
            if (ch == '\\' || ch == '^' || ch == '-' || ch == ']' ) {
                builder.append('\\');
            }
            builder.append(ch);
        }
        return builder.toString();
    }
}


