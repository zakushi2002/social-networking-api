package com.social.networking.api.form.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.networking.api.model.Notification;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.service.RabbitMQService;
import com.social.networking.api.service.RabbitSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for handling notifications.
 */
@Service
@Slf4j
public class NotificationService {
    @Autowired
    RabbitMQService rabbitMQService;

    /**
     * SOCIAL application name.
     */
    @Value("${rabbitmq.app}")
    private String elmsAppName;

    /**
     * Queue name for notifications.
     */
    @Value("${rabbitmq.notification.queue}")
    private String queueName;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RabbitSender rabbitSender;

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Sends a message to the notification queue.
     *
     * @param message the message to send
     * @param kind    the notification kind
     * @param userId  the user ID
     */
    public void sendMessage(String message, Integer kind, Long userId) {
        PostNotificationData data = new PostNotificationData();
        data.setMessage(message);
        data.setCmd(NotificationConstant.BACKEND_POST_NOTIFICATION_CMD);
        data.setKind(kind);
        data.setMessage(message);
        data.setUserId(userId);
        data.setApp(elmsAppName);
        handleSendMsg(data, NotificationConstant.BACKEND_POST_NOTIFICATION_CMD);
    }

    /**
     * Handles sending a message to the notification queue.
     *
     * @param data the data to send
     * @param cmd  the command
     * @param <T>  the data type
     */
    private <T> void handleSendMsg(T data, String cmd) {
        BaseSendMsgForm<T> form = new BaseSendMsgForm<>();
        form.setApp(NotificationConstant.BACKEND_APP);
        form.setCmd(cmd);
        form.setData(data);

        String msg;
        try {
            msg = objectMapper.writeValueAsString(form);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // create queue if existed
        rabbitMQService.createQueueIfNotExist(queueName);
        // push msg
        rabbitSender.send(msg, queueName);
    }

    /**
     * Creates a notification.
     *
     * @param state the notification state
     * @param kind  the notification kind
     * @return the created notification
     */
    public Notification createNotification(Integer state, Integer kind) {
        Notification notification = new Notification();
        notification.setKind(kind);
        notification.setState(state);
        notificationRepository.save(notification);
        return notification;
    }

    /**
     * Creates a notification with a reference ID.
     *
     * @param state the notification state
     * @param kind  the notification kind
     * @param refId the reference ID
     * @return the created notification
     */
    public Notification createNotificationWithRefId(Integer state, Integer kind, String refId) {
        Notification notification = new Notification();
        notification.setKind(kind);
        notification.setState(state);
        notification.setRefId(refId);
        notificationRepository.save(notification);
        return notification;
    }

    /**
     * This method converts an object into a JSON string using the ObjectMapper class.
     *
     * @param object the object to be converted into a JSON string
     * @return the JSON string representation of the object or null if an error occurs
     */
    public String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
