package com.social.networking.api.utils;

public class ConvertUtils {
    private ConvertUtils() {

    }

    public static Long convertStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static int convertToCent(double b) {
        int i = (int) (b);
        double k = b - (double) i;
        if (k > 0.5 && k < 1) {
            i += 1;
        }
        return i;
    }
}
