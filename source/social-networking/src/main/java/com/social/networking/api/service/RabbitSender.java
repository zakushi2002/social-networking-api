package com.social.networking.api.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Service class for sending messages to RabbitMQ.
 */
@Service
@Slf4j
public class RabbitSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     * Sends a message to a RabbitMQ queue.
     *
     * @param message   the message to send
     * @param queueName the name of the queue to send the message to
     */
    public void send(String message, String queueName) {
        createQueueIfNotExist(queueName);
        if (message == null || StringUtils.isBlank(message)) {
            log.warn("[RabbitMQ] Message is null or empty, not sending to queue: {}", queueName);
            return;
        }
        template.convertAndSend(queueName, message);
        log.info("[RabbitMQ] Sent message: {} to queue: {}", message, queueName);
    }

    /**
     * Checks if a queue exists.
     *
     * @param queueName the name of the queue to check
     * @return true if the queue exists, false otherwise
     */
    public boolean isQueueExist(String queueName) {
        Properties properties = rabbitAdmin.getQueueProperties(queueName);
        return properties != null;
    }

    /**
     * Creates a queue if it does not exist.
     *
     * @param queueName the name of the queue to create
     */
    public void createQueueIfNotExist(String queueName) {
        if (!isQueueExist(queueName)) {
            log.info("[RabbitMQ] Creating queue: {}", queueName);
            rabbitAdmin.declareQueue(new Queue(queueName));
        }
    }

    /**
     * Deletes a queue.
     *
     * @param queueName the name of the queue to delete
     */
    public void removeQueue(String queueName) {
        if (isQueueExist(queueName)) {
            log.info("[RabbitMQ] Deleting queue: {}", queueName);
            rabbitAdmin.deleteQueue(queueName);
        }
    }
}
