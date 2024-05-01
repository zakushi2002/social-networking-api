package com.social.networking.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for RabbitMQ operations.
 */
@Service
@Slf4j
public class RabbitMQService {
    /**
     * RestTemplate for making HTTP requests.
     */
    @Autowired
    RestTemplate restTemplate;

    /**
     * RabbitMQSender for sending messages to RabbitMQ.
     */
    @Autowired
    RabbitSender rabbitSender;

    /**
     * Creates a RabbitMQ queue if it does not exist.
     *
     * @param queueName name of the queue to be created
     */
    public void createQueueIfNotExist(String queueName) {
        rabbitSender.createQueueIfNotExist(queueName);
    }
}
