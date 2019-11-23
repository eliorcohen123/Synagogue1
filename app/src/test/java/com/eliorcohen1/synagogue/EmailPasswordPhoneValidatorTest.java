package com.eliorcohen1.synagogue;

import com.eliorcohen1.synagogue.StartPackage.EmailPasswordPhoneValidator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the EmailPasswordPhoneValidator logic.
 */
public class EmailPasswordPhoneValidatorTest {

    /**
     * Test Emails.
     */
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailPasswordPhoneValidator.isValidEmail("name@email.com"));
    }

    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailPasswordPhoneValidator.isValidEmail("name@email.co.uk"));
    }

    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidEmail("name@email"));
    }

    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidEmail("name@email..com"));
    }

    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidEmail("@email.com"));
    }

    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidEmail(""));
    }

    /**
     * Test Passwords.
     */
    @Test
    public void passwordValidator_CorrectPasswordSimpleNumbers_ReturnsTrue() {
        assertTrue(EmailPasswordPhoneValidator.isValidPassword("123456"));
    }

    @Test
    public void passwordValidator_CorrectPasswordSimpleSmallWords_ReturnsTrue() {
        assertTrue(EmailPasswordPhoneValidator.isValidPassword("qwerty"));
    }

    @Test
    public void passwordValidator_CorrectPasswordSimpleBigWords_ReturnsTrue() {
        assertTrue(EmailPasswordPhoneValidator.isValidPassword("QWERTY"));
    }

    @Test
    public void passwordValidator_CorrectPasswordMulti_ReturnsTrue() {
        assertTrue(EmailPasswordPhoneValidator.isValidPassword("12qwER"));
    }

    @Test
    public void passwordValidator_InvalidEmptyPassword_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidPassword(""));
    }

    @Test
    public void passwordValidator_InvalidPasswordTooMuch_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidPassword("1234567qwertyuQWERTYU"));
    }

    /**
     * Test PhoneNumbers.
     */
    @Test
    public void phoneNumbersValidator_CorrectPhoneNumbersSimpleNumbers_ReturnsTrue() {
        assertTrue(EmailPasswordPhoneValidator.isValidPhoneNumber("0501234567"));
    }

    @Test
    public void phoneNumbersValidator_InvalidEmptyPhoneNumbers_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidPhoneNumber(""));
    }

    @Test
    public void phoneNumbersValidator_InvalidPhoneNumbersTooMuch_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidPhoneNumber("05012345678"));
    }

    @Test
    public void phoneNumbersValidator_InvalidPhoneNumbersLittleToo_ReturnsFalse() {
        assertFalse(EmailPasswordPhoneValidator.isValidPhoneNumber("050123456"));
    }

}
