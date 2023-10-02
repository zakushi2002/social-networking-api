package com.social.networking.api.view.validation.implement;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.view.validation.PostKind;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class PostKindValidation implements ConstraintValidator<PostKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(PostKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer postKind, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (postKind == null && allowNull) {
            return true;
        }
        return Objects.equals(postKind, SocialNetworkingConstant.POST_KIND_NORMAL)
                || Objects.equals(postKind, SocialNetworkingConstant.POST_KIND_FORUM);
    }
}
