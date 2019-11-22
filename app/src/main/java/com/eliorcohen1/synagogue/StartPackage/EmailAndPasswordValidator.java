package com.eliorcohen1.synagogue.StartPackage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAndPasswordValidator {

    private static String EMAIL_PATTERN =
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+";
    private static String PASSWORD_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,20}";
    private static Pattern pattern;
    private static Matcher matcher;

    public static boolean isValidEmail(final String password) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidPassword(final String password) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

}
