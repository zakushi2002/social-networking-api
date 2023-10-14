package com.social.networking.api.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class CommonAsyncService {
    @Autowired
    private EmailService emailService;
    @Autowired
    @Qualifier("threadPoolExecutor")
    @Getter
    private TaskExecutor taskExecutor;

    @Async
    public void sendEmail(String email, Map<String, Object> variables, String subject) {
        Runnable task3 = () -> {
            try {
                emailService.sendEmail(email, variables, subject);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        };
        taskExecutor.execute(task3);
    }
}
