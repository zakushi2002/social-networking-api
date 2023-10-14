package com.social.networking.api.service;

import java.util.Map;

public interface ThymeleafService {
    String createContentGetOTP(String template, Map<String, Object> variables);
}
