package com.eliorcohen1.synagogue;

import com.eliorcohen1.synagogue.StartPackage.EmailAndPasswordValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the EmailAndPasswordValidator logic.
 */
public class EmailAndPasswordValidatorTest {

    // Test Emails
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailAndPasswordValidator.isValidEmail("name@email.com"));
    }

    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailAndPasswordValidator.isValidEmail("name@email.co.uk"));
    }

    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailAndPasswordValidator.isValidEmail("name@email"));
    }

    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailAndPasswordValidator.isValidEmail("name@email..com"));
    }

    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailAndPasswordValidator.isValidEmail("@email.com"));
    }

    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailAndPasswordValidator.isValidEmail(""));
    }

    // Test Passwords
    @Test
    public void passwordValidator_CorrectPasswordSimpleNumbers_ReturnsTrue() {
        assertTrue(EmailAndPasswordValidator.isValidPassword("123456"));
    }

    @Test
    public void passwordValidator_CorrectPasswordSimpleSmallWords_ReturnsTrue() {
        assertTrue(EmailAndPasswordValidator.isValidPassword("qwerty"));
    }

    @Test
    public void passwordValidator_CorrectPasswordSimpleBigWords_ReturnsTrue() {
        assertTrue(EmailAndPasswordValidator.isValidPassword("QWERTY"));
    }

    @Test
    public void passwordValidator_CorrectPasswordMulti_ReturnsTrue() {
        assertTrue(EmailAndPasswordValidator.isValidPassword("12qwER"));
    }

    @Test
    public void passwordValidator_InvalidEmptyPassword_ReturnsFalse() {
        assertFalse(EmailAndPasswordValidator.isValidPassword(""));
    }

    @Test
    public void passwordValidator_InvalidPasswordTooMuch_ReturnsFalse() {
        assertFalse(EmailAndPasswordValidator.isValidPassword("1234567qwertyuQWERTYU"));
    }

}
