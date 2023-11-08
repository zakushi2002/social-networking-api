package com.social.networking.api.view.dto.profile.oauth2;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.oauth.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2ProfileDtoFactory {
    public static OAuth2ProfileDto getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(SocialNetworkingConstant.PROVIDER_GOOGLE)) {
            return new GoogleOAuth2ProfileDto(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
