package com.social.networking.api.constant;

public class SocialNetworkingConstant {
    /**
     * Date format constant
     */
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /**
     * Security Constant for grant type
     */
    public static final String GRANT_TYPE_PASSWORD = "password";
    private SocialNetworkingConstant() {
        throw new IllegalStateException("Utility class");
    }
}
