package com.eliorcohen1.synagogue.StartPackage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPasswordPhoneValidator {

    private static volatile EmailPasswordPhoneValidator sInstance;

    private EmailPasswordPhoneValidator() {
        if (sInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static EmailPasswordPhoneValidator getInstance() {
        if (sInstance == null) {
            synchronized (EmailPasswordPhoneValidator.class) {
                if (sInstance == null) sInstance = new EmailPasswordPhoneValidator();
            }
        }

        return sInstance;
    }

    private static String EMAIL_PATTERN =
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+";
    private static String PASSWORD_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,20}";
    private static String PHONE_NUMBER_PATTERN = "[0-9]{10}$";
    private static Pattern pattern;
    private static Matcher matcher;

    public  boolean isValidEmail(final String password) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public  boolean isValidPassword(final String password) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public  boolean isValidPhoneNumber(final String phone_number) {
        pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        matcher = pattern.matcher(phone_number);

        return matcher.matches();
    }

}
