package com.thanhtd.glassstore.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    private static final String EMAIL_REGEXP = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean checkValidEmailAddress(String email) {
        email = email.trim();
        if (email.startsWith("postmaster@") || email.startsWith("root@"))
            return false;
        Pattern mask = Pattern.compile(EMAIL_REGEXP);
        Matcher matcher = mask.matcher(email);
        if (matcher.matches()) {
            String localPath = email.substring(0, email.indexOf("@"));
            return localPath.length() >= 5 && localPath.length() <= 32;
        }
        return false;
    }
}
