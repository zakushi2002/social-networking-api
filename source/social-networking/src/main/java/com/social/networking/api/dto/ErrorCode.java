package com.social.networking.api.dto;

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
    public static final String ACCOUNT_ERROR_OTP_INVALID = "ERROR-ACCOUNT-004";
    public static final String ACCOUNT_ERROR_OTP_EXPIRED = "ERROR-ACCOUNT-005";
    public static final String ACCOUNT_ERROR_LOCKED = "ERROR-ACCOUNT-006";
    public static final String ACCOUNT_ERROR_NOT_SEND_REQUEST_OTP = "ERROR-ACCOUNT-007";
    public static final String ACCOUNT_ERROR_SENT_REQUEST_OTP = "ERROR-ACCOUNT-008";

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
    public static final String POST_ERROR_HANDLED = "ERROR-POST-002"; // Post has been approved or rejected
    public static final String POST_ERROR_TITLE_REQUIRED = "ERROR-POST-003";

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

    /**
     * Relationship error code
     */
    public static final String RELATIONSHIP_ERROR_ALREADY_FOLLOWED = "ERROR-RELATIONSHIP-000";
    public static final String RELATIONSHIP_ERROR_NOT_FOLLOW_YOURSELF = "ERROR-RELATIONSHIP-001";
    public static final String RELATIONSHIP_ERROR_NOT_FOUND = "ERROR-RELATIONSHIP-002";
    public static final String RELATIONSHIP_ERROR_NO_OWNERSHIP = "ERROR-RELATIONSHIP-003";

    /**
     * Report error code
     */
    public static final String REPORT_ERROR_NOT_FOUND = "ERROR-REPORT-000";
    public static final String REPORT_ERROR_REPORT_YOURSELF = "ERROR-REPORT-001";
    public static final String REPORT_ERROR_APPROVED_OR_REJECTED = "ERROR-REPORT-002";

    /**
     * Conversation error code
     */
    public static final String CONVERSATION_ERROR_NOT_FOUND = "ERROR-CONVERSATION-000";
    public static final String CONVERSATION_ERROR_ACCOUNT_ID_EMPTY = "ERROR-CONVERSATION-001";
    public static final String CONVERSATION_ERROR_ACCOUNT_ID_NOT_FOUND = "ERROR-CONVERSATION-002";
    public static final String CONVERSATION_ERROR_NOT_MEMBER = "ERROR-CONVERSATION-003";

    /**
     * Message error code
     */
    public static final String MESSAGE_ERROR_NOT_FOUND = "ERROR-MESSAGE-000";
    public static final String MESSAGE_ERROR_NOT_OWNER = "ERROR-MESSAGE-001";

    /**
     * Notification error code
     */
    public static final String NOTIFICATION_ERROR_NOT_FOUND = "ERROR-NOTIFICATION-000";
}
