package com.social.networking.api.message.comment;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.comment.CommentNotificationMessage;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.Comment;
import com.social.networking.api.model.Notification;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentMessage implements MessageService<Comment> {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotificationAndSendMessage(Integer notificationState, Comment data, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        // Adds the post owner to the notifications list if the comment is a level 1 comment and the comment owner is not the post owner.
        if (data.getPost().getAccount() != null
                && data.getDepth().equals(SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_1)
                && !data.getAccount().getId().equals(data.getPost().getAccount().getId())) {
            Notification notification = createNotification(data, notificationState, notificationKind, data.getPost().getAccount().getId());
            notifications.add(notification);
        }
        // Sends a message for each notification
        for (Notification notification : notifications) {
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        }
    }

    @Override
    public Notification createNotification(Comment data, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(data, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_COMMENT_IN_MY_POST)) {
            notification.setRefId(data.getId().toString());
        }
        return notification;
    }

    @Override
    public String getJsonMessage(Comment data, Notification notification) {
        CommentNotificationMessage commentNotificationMessage = new CommentNotificationMessage();
        commentNotificationMessage.setNotificationId(notification.getId());
        commentNotificationMessage.setCommentId(data.getId());
        commentNotificationMessage.setPostId(data.getPost().getId());
        commentNotificationMessage.setCommentDepth(data.getDepth());
        commentNotificationMessage.setAccountId(data.getAccount().getId());
        commentNotificationMessage.setAccountName(data.getAccount().getFullName());
        commentNotificationMessage.setAccountAvatar(data.getAccount().getAvatarPath());
        return notificationService.convertObjectToJson(commentNotificationMessage);
    }
}
