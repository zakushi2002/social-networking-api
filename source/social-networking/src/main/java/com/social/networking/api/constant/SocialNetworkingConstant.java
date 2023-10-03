package com.social.networking.api.constant;

import com.social.networking.api.utils.ConfigurationService;

public class SocialNetworkingConstant {
    public static final String ROOT_DIRECTORY = ConfigurationService.getInstance().getString("file.upload-dir","/tmp/upload");

    /**
     * Date format constant
     */
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /**
     * Security Constant for grant type
     */
    public static final String GRANT_TYPE_PASSWORD = "password";

    /**
     * Account kind constant
     */
    public static final Integer ACCOUNT_KIND_ADMIN = 1;
    public static final Integer ACCOUNT_KIND_USER = 2;
    public static final Integer ACCOUNT_KIND_EXPERT = 3;

    /**
     * Status constant
     */
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;

    /**
     * Post kind constant
     */
    public static final Integer POST_KIND_NORMAL = 1;
    public static final Integer POST_KIND_FORUM = 2;

    /**
     * Privacy constant
     */
    public static final Integer PRIVACY_PUBLIC = 1;
    public static final Integer PRIVACY_PRIVATE = 2;

    /**
     * Comment depth constant
     */
    public static final Integer COMMENT_DEPTH_LEVEL_1 = 1;
    public static final Integer COMMENT_DEPTH_LEVEL_2 = 2;
    public static final Integer COMMENT_DEPTH_LEVEL_3 = 3;

    /**
     * Reaction kind constant
     */
    public static final Integer REACTION_KIND_LOVE = 1;
    public static final Integer REACTION_KIND_LIKE = 2;
    public static final Integer REACTION_KIND_HAHA = 3;
    public static final Integer REACTION_KIND_WOW = 4;
    public static final Integer REACTION_KIND_SAD = 5;
    public static final Integer REACTION_KIND_ANGRY = 6;

    /**
     * Category kind constant
     */
    public static final Integer CATEGORY_KIND_HOSPITAL = 1;
    public static final Integer CATEGORY_KIND_HOSPITAL_ROLE = 2;
    public static final Integer CATEGORY_KIND_DEPARTMENT = 3;
    public static final Integer CATEGORY_KIND_ACADEMIC_DEGREE = 4;

    private SocialNetworkingConstant() {
        throw new IllegalStateException("Utility class");
    }
}
