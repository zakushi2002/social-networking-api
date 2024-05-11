package com.social.networking.api.service;

import com.social.networking.api.model.Notification;

public interface MessageService<T> {
    /**
     * Creates a notification and sends a message for the given data and notification kind.
     *
     * @param notificationState the state of the notification
     * @param data              the data for which to create the notification
     * @param notificationKind  the kind of notification to create
     */
    void createNotificationAndSendMessage(Integer notificationState, T data, Integer notificationKind);

    /**
     * Creates a notification for the given data and notification kind.
     *
     * @param data              the data for which to create the notification
     * @param notificationState the state of the notification
     * @param notificationKind  the kind of notification to create
     * @param accountId         the ID of the account for which to create the notification
     * @return the created notification
     */
    Notification createNotification(T data, Integer notificationState, Integer notificationKind, Long accountId);

    /**
     * Creates a JSON message for the given data and notification.
     *
     * @param data         the data for which to create the message
     * @param notification the notification for which to create the message
     * @return the JSON message
     */
    String getJsonMessage(T data, Notification notification);
}
