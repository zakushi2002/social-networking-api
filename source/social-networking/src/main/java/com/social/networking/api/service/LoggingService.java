package com.social.networking.api.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {
    void logRequest(HttpServletRequest request, Object body);

    void logResponse(HttpServletRequest request, HttpServletResponse response, Object body);
}
