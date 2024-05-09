package com.social.networking.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.service.impl.UserServiceImpl;
import com.social.networking.api.dto.profile.oauth2.OAuth2ProfileDto;
import com.social.networking.api.dto.profile.oauth2.OAuth2ProfileDtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public class CustomTokenGranter extends AbstractTokenGranter {

    private UserServiceImpl userService;
    private AuthenticationManager authenticationManager;

    protected CustomTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    @Autowired
    public CustomTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType, UserServiceImpl userService) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        return super.getOAuth2Authentication(client, tokenRequest);
    }

    protected OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest) {
        try {
            if (SocialNetworkingConstant.GRANT_TYPE_GOOGLE.equalsIgnoreCase(tokenRequest.getGrantType())) {
                Map<String, Object> attributes = new ObjectMapper().readValue(tokenRequest.getRequestParameters().get("google"), Map.class);
                OAuth2ProfileDto oAuth2ProfileDto = OAuth2ProfileDtoFactory.getOAuth2UserInfo(SocialNetworkingConstant.GRANT_TYPE_GOOGLE, attributes);
                return userService.getAccessTokenForGoogle(authenticationManager, client, tokenRequest, oAuth2ProfileDto, this.getTokenServices());
            }
            String email = tokenRequest.getRequestParameters().get("email");
            String password = tokenRequest.getRequestParameters().get("password");
            return userService.getAccessTokenForMultipleTenancies(client, tokenRequest, email, password, this.getTokenServices());
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            throw new InvalidTokenException("Account invalid");
        }
    }
}
