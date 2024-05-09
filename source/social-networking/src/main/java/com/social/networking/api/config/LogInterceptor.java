package com.social.networking.api.config;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.UserLockedException;
import com.social.networking.api.jwt.SocialNetworkingJwt;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.service.LoggingService;
import com.social.networking.api.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Autowired
    LoggingService loggingService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name())) {
            loggingService.logRequest(request, null);
        }
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        log.debug("Starting call url: [{}]", getUrl(request));

        SocialNetworkingJwt jwt = userService.getAdditionalInfo();
        if (jwt != null
                && accountRepository.existsByIdAndKindAndStatus(jwt.getAccountId(), jwt.getUserKind(), SocialNetworkingConstant.STATUS_LOCK)) {
            throw new UserLockedException("Locked account!");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        log.debug("Complete [{}] executeTime : {}ms", getUrl(request), executeTime);

        if (ex != null) {
            log.error("afterCompletion>> {}", ex.getMessage());
        }
    }

    /**
     * Get full URL request
     *
     * @param request
     * @return URL
     */
    private static String getUrl(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();   // kind=123
        if (!StringUtils.isEmpty(queryString)) {
            requestUrl += "?" + queryString;
        }
        return requestUrl;
    }
}
