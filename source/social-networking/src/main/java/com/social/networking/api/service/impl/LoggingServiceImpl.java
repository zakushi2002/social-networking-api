package com.social.networking.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.networking.api.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class LoggingServiceImpl implements LoggingService {
    @Override
    public void logRequest(HttpServletRequest request, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(request);
        ObjectMapper mapper = new ObjectMapper();

        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(request.getMethod()).append("] ");
        stringBuilder.append("path=[").append(request.getRequestURI()).append("] ");
        stringBuilder.append("headers=[").append(buildHeadersMap(request)).append("] ");

        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            try {
                stringBuilder.append("body=[" + mapper.writeValueAsString(body) + "]");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        log.info(stringBuilder.toString());
    }

    @Override
    public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        StringBuilder stringBuilder = new StringBuilder();

        ObjectMapper mapper = new ObjectMapper();
        stringBuilder.append("RESPONSE ");
        stringBuilder.append("method=[").append(request.getMethod()).append("] ");
        stringBuilder.append("path=[").append(request.getRequestURI()).append("] ");
        try {
            String bodyResponse = mapper.writeValueAsString(body);
            stringBuilder.append("responseBody=[").append(bodyResponse).append("] ");
            log.info(stringBuilder.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> buildParametersMap(HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            resultMap.put(key, value);
        }
        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
