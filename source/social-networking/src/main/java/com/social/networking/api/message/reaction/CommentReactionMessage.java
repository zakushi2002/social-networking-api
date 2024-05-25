package com.social.networking.api.message.reaction;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.reaction.CommentReactionNotificationMessage;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.CommentReaction;
import com.social.networking.api.model.Notification;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentReactionMessage implements MessageService<CommentReaction> {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotificationAndSendMessage(Integer notificationState, CommentReaction data, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        if (data.getComment().getAccount() != null) {
            // Creates a notification for the given reaction and notification kind
            Notification notification = createNotification(data, notificationState, notificationKind, data.getComment().getAccount().getId());
            notifications.add(notification);
        }
        // Saves the notifications to the database
        notificationRepository.saveAll(notifications);
        // Sends a message for each notification
        for (Notification notification : notifications) {
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        }
    }

    public Notification createNotification(CommentReaction data, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(data, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_REACTION_MY_COMMENT)) {
            notificationRepository.deleteAllByIdUserAndKindAndRefId(accountId, SocialNetworkingConstant.NOTIFICATION_KIND_REACTION_MY_COMMENT, data.getComment().getId().toString());
            notification.setRefId(data.getComment().getId().toString());
        }
        return notification;
    }

    public String getJsonMessage(CommentReaction data, Notification notification) {
        CommentReactionNotificationMessage commentReactionNotificationMessage = new CommentReactionNotificationMessage();
        commentReactionNotificationMessage.setNotificationId(notification.getId());
        commentReactionNotificationMessage.setCommentReactionId(data.getId());
        commentReactionNotificationMessage.setReactionKind(data.getKind());
        commentReactionNotificationMessage.setPostId(data.getComment().getPost().getId());
        commentReactionNotificationMessage.setCommentId(data.getComment().getId());
        commentReactionNotificationMessage.setCommentContent(data.getComment().getContent());
        commentReactionNotificationMessage.setAccountId(data.getAccount().getId());
        commentReactionNotificationMessage.setAccountName(data.getAccount().getFullName());
        commentReactionNotificationMessage.setAccountAvatar(data.getAccount().getAvatarPath());
        return notificationService.convertObjectToJson(commentReactionNotificationMessage);
    }
}
