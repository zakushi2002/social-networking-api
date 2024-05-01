package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.jwt.SocialNetworkingJwt;
import com.social.networking.api.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
public class BaseController {
    @Autowired
    private UserServiceImpl userService;

    public long getCurrentUser() {
        SocialNetworkingJwt socialNetworkingJwt = userService.getAdditionalInfo();
        return socialNetworkingJwt.getAccountId();
    }

    public long getTokenId() {
        SocialNetworkingJwt socialNetworkingJwt = userService.getAdditionalInfo();
        return socialNetworkingJwt.getTokenId();
    }

    public SocialNetworkingJwt getSessionFromToken() {
        return userService.getAdditionalInfo();
    }

    public boolean isSuperAdmin() {
        SocialNetworkingJwt socialNetworkingJwt = userService.getAdditionalInfo();
        if (socialNetworkingJwt != null) {
            return socialNetworkingJwt.getIsSuperAdmin();
        }
        return false;
    }

    public boolean isExpert(){
        SocialNetworkingJwt socialNetworkingJwt = userService.getAdditionalInfo();
        if (socialNetworkingJwt != null){
            return Objects.equals(socialNetworkingJwt.getUserKind(), SocialNetworkingConstant.ACCOUNT_KIND_EXPERT);
        }
        return false;
    }
}
