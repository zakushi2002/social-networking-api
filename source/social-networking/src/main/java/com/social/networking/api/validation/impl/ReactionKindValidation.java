package com.social.networking.api.validation.impl;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.validation.ReactionKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ReactionKindValidation implements ConstraintValidator<ReactionKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(ReactionKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer reactionKind, ConstraintValidatorContext constraintValidatorContext) {
        if (reactionKind == null && allowNull) {
            return true;
        }
        return Objects.equals(reactionKind, SocialNetworkingConstant.REACTION_KIND_LIKE)
                || Objects.equals(reactionKind, SocialNetworkingConstant.REACTION_KIND_LOVE)
                || Objects.equals(reactionKind, SocialNetworkingConstant.REACTION_KIND_HAHA)
                || Objects.equals(reactionKind, SocialNetworkingConstant.REACTION_KIND_WOW)
                || Objects.equals(reactionKind, SocialNetworkingConstant.REACTION_KIND_SAD)
                || Objects.equals(reactionKind, SocialNetworkingConstant.REACTION_KIND_ANGRY);
    }
}
