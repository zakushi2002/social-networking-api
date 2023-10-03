package com.social.networking.api.view.dto;

public class ErrorCode {
    /**
     * General error code
     */
    public static final String GENERAL_ERROR_UNAUTHORIZED = "ERROR-GENERAL-000";

    /**
     * Permission error code
     */
    public static final String PERMISSION_ERROR_NAME_EXIST = "ERROR-PERMISSION-000";
    public static final String PERMISSION_ERROR_CODE_EXIST = "ERROR-PERMISSION-001";
    public static final String PERMISSION_ERROR_NOT_FOUND = "ERROR-PERMISSION-002";

    /**
     * Group error code
     */
    public static final String GROUP_ERROR_NAME_EXIST = "ERROR-GROUP-000";
    public static final String GROUP_ERROR_NOT_FOUND = "ERROR-GROUP-001";

    /**
     * Account error code
     */
    public static final String ACCOUNT_ERROR_EMAIL_EXIST = "ERROR-ACCOUNT-000";
    public static final String ACCOUNT_ERROR_NOT_FOUND = "ERROR-ACCOUNT-001";
    public static final String ACCOUNT_ERROR_PASSWORD_INVALID = "ERROR-ACCOUNT-002";
    public static final String ACCOUNT_ERROR_WRONG_PASSWORD = "ERROR-ACCOUNT-003";

    /**
     * User profile error code
     */
    public static final String USER_PROFILE_ERROR_NOT_FOUND = "ERROR-USER-PROFILE-000";
    public static final String USER_PROFILE_ERROR_PHONE_EMPTY = "ERROR-USER-PROFILE-001";

    /**
     * Expert profile error code
     */
    public static final String EXPERT_PROFILE_ERROR_NOT_FOUND = "ERROR-EXPERT-PROFILE-000";
    public static final String EXPERT_PROFILE_ERROR_PHONE_EMPTY = "ERROR-EXPERT-PROFILE-001";

    /**
     * Post error code
     */
    public static final String POST_ERROR_NOT_FOUND = "ERROR-POST-000";
    public static final String POST_ERROR_NOT_OWNER = "ERROR-POST-001";

    /**
     * Comment error code
     */
    public static final String COMMENT_ERROR_NOT_FOUND = "ERROR-COMMENT-000";
    public static final String COMMENT_ERROR_NOT_OWNER = "ERROR-COMMENT-001";

    /**
     * Reaction error code
     */
    public static final String POST_REACTION_ERROR_EXIST = "ERROR-REACTION-000";
    public static final String POST_REACTION_ERROR_NOT_OWNER = "ERROR-REACTION-001";
    public static final String POST_REACTION_ERROR_NOT_FOUND = "ERROR-REACTION-002";
    public static final String COMMENT_REACTION_ERROR_EXIST = "ERROR-REACTION-003";
    public static final String COMMENT_REACTION_ERROR_NOT_OWNER = "ERROR-REACTION-004";
    public static final String COMMENT_REACTION_ERROR_NOT_FOUND = "ERROR-REACTION-005";

    /**
     * Category error code
     */
    public static final String CATEGORY_ERROR_NOT_FOUND = "ERROR-CATEGORY-000";
    public static final String CATEGORY_ERROR_NAME_EXIST_IN_KIND = "ERROR-CATEGORY-001";

    /**
     * Bookmark error code
     */
    public static final String BOOKMARK_ERROR_EXIST = "ERROR-BOOKMARK-000";
    public static final String BOOKMARK_ERROR_NOT_FOUND = "ERROR-BOOKMARK-001";
}
