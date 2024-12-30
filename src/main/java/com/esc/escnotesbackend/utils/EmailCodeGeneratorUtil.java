package com.esc.escnotesbackend.utils;

import java.security.SecureRandom;

public class EmailCodeGeneratorUtil {
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int defaultLength = 6;

    private EmailCodeGeneratorUtil() {}

    public static String getEmailCode(int length) {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            SecureRandom random = new SecureRandom();

            int number = random.nextInt(characters.length());
            code.append(characters.charAt(number));
        }

        return code.toString();
    }

    public static String getEmailCode() {
        return getEmailCode(defaultLength);
    }
}
