package com.social.networking.api.view.validation.implement;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.view.validation.ConversationKind;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class ConversationKindValidation implements ConstraintValidator<ConversationKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(ConversationKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer conversationKind, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (conversationKind == null && allowNull) {
            return true;
        }
        return Objects.equals(conversationKind, SocialNetworkingConstant.CONVERSATION_KIND_GROUP)
                || Objects.equals(conversationKind, SocialNetworkingConstant.CONVERSATION_KIND_PRIVATE);
    }
}
