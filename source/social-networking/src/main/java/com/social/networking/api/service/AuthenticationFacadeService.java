package com.social.networking.api.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeService {
    Authentication getAuthentication();
}
