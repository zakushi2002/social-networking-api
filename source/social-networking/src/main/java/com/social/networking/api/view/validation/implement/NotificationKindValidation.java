package com.social.networking.api.view.validation.implement;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.view.validation.NotificationKind;

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
        return Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_POST)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_COMMENT)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_REACTION)
                || Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_FOLLOW);
    }
}
