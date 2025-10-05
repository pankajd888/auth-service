package com.digi.auth.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidPasswordValidatorTest {

    private ValidPasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ValidPasswordValidator();
    }

    @Test
    void returnsFalseWhenNull() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void returnsFalseWhenTooShortOrTooLong() {
        assertFalse(validator.isValid("A1@bcde", null));
        assertFalse(validator.isValid("A1@" + "a".repeat(30) + "b", null));
    }

    @Test
    void returnsFalseWhenMissingUppercase() {
        assertFalse(validator.isValid("a1@bcdef", null));
    }

    @Test
    void returnsFalseWhenMissingDigit() {
        assertFalse(validator.isValid("A@bcdefg", null));
    }

    @Test
    void returnsFalseWhenMissingAllowedSpecial() {
        assertFalse(validator.isValid("A1bcdefg", null));
    }

    @Test
    void returnsFalseWhenContainsDisallowedCharacters() {
        assertFalse(validator.isValid("A1@bc def", null));
        assertFalse(validator.isValid("A1@bc-def", null));
        assertFalse(validator.isValid("A1@bc(def)", null));
    }

    @Test
    void returnsTrueForValidPasswordsWithDefaultAllowedSpecials() {
        assertTrue(validator.isValid("A1@bcdef", null));
        assertTrue(validator.isValid("Z9#Abcdef", null));
        assertTrue(validator.isValid("Q8%AlphaNum", null));
        assertTrue(validator.isValid("M7_Beta!Gamma", null));
        assertTrue(validator.isValid("X1.delta!", null));
    }
}


