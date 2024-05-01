package com.social.networking.api.validation.impl;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.validation.NotificationKind;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class NotificationKindValidation implements ConstraintValidator<NotificationKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(NotificationKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer notificationKind, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (notificationKind == null && allowNull) {
            return true;
        }
        return Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_NEW_POST_OF_FOLLOWING)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_COMMENT_IN_MY_POST)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_REACTION_MY_POST)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_REACTION_MY_COMMENT)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_NEW_FOLLOWER)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_COURSE_APPROVED)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_TAGGED_IN_COMMENT);
    }
}
