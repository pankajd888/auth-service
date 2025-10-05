package com.digi.auth.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidUsernameValidatorTest {

    private ValidUsernameValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ValidUsernameValidator();
    }

    @Test
    void returnsFalseWhenNull() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void returnsFalseWhenBlank() {
        assertFalse(validator.isValid("", null));
        assertFalse(validator.isValid("   ", null));
    }

    @Test
    void returnsFalseWhenTooShortOrTooLong() {
        assertFalse(validator.isValid("abc12", null));
        assertFalse(validator.isValid("abcdefghijklmnop", null));
    }

    @Test
    void returnsFalseWhenContainsInvalidCharacters() {
        assertFalse(validator.isValid("user-name", null));
        assertFalse(validator.isValid("user.name", null));
        assertFalse(validator.isValid("user@name", null));
    }

    @Test
    void returnsTrueForValidUsernames() {
        assertTrue(validator.isValid("user_01", null));
        assertTrue(validator.isValid("Alpha123", null));
        assertTrue(validator.isValid("_______", null));
        assertTrue(validator.isValid("A1b2C3", null));
        assertTrue(validator.isValid("Zyxwv_98", null));
    }
}


