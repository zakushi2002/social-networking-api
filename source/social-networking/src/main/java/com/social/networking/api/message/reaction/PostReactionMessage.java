package com.social.networking.api.message.reaction;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.reaction.PostReactionNotificationMessage;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.Notification;
import com.social.networking.api.model.PostReaction;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostReactionMessage implements MessageService<PostReaction> {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotificationAndSendMessage(Integer notificationState, PostReaction data, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        if (data.getPost().getAccount() != null) {
            // Creates a notification for the given reaction and notification kind
            Notification notification = createNotification(data, notificationState, notificationKind, data.getPost().getAccount().getId());
            notifications.add(notification);
        }
        // Saves the notifications to the database
        notificationRepository.saveAll(notifications);
        // Sends a message for each notification
        for (Notification notification : notifications) {
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        }
    }

    @Override
    public Notification createNotification(PostReaction data, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(data, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_REACTION_MY_POST)) {
            notificationRepository.deleteAllByIdUserAndKindAndRefId(accountId, SocialNetworkingConstant.NOTIFICATION_KIND_REACTION_MY_POST, data.getPost().getId().toString());
            notification.setRefId(data.getPost().getId().toString());
        }
        return notification;
    }

    @Override
    public String getJsonMessage(PostReaction data, Notification notification) {
        PostReactionNotificationMessage postReactionNotificationMessage = new PostReactionNotificationMessage();
        postReactionNotificationMessage.setNotificationId(notification.getId());
        postReactionNotificationMessage.setPostReactionId(data.getId());
        postReactionNotificationMessage.setReactionKind(data.getKind());
        postReactionNotificationMessage.setPostId(data.getPost().getId());
        postReactionNotificationMessage.setPostTitle(data.getPost().getTitle());
        postReactionNotificationMessage.setAccountId(data.getAccount().getId());
        postReactionNotificationMessage.setAccountName(data.getAccount().getFullName());
        postReactionNotificationMessage.setAccountAvatar(data.getAccount().getAvatarPath());
        return notificationService.convertObjectToJson(postReactionNotificationMessage);
    }
}
