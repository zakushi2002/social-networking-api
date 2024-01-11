package com.social.networking.api.dto.profile.oauth2;

import java.util.Map;

public abstract class OAuth2ProfileDto {
    protected Map<String, Object> attributes;

    public OAuth2ProfileDto(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
