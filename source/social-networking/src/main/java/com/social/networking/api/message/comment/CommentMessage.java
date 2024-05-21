package com.social.networking.api.message.comment;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.comment.CommentDto;
import com.social.networking.api.dto.comment.CommentNotificationMessage;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.Notification;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentMessage implements MessageService<CommentDto> {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotificationAndSendMessage(Integer notificationState, CommentDto data, Integer notificationKind) {
        if (data.getOwnerIdOfPost() != null
                && Objects.equals(data.getCommentDepth(), SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_1)
                && !Objects.equals(data.getOwner().getId(), data.getOwnerIdOfPost())) {
            Notification notification = createNotification(data, notificationState, notificationKind, data.getOwnerIdOfPost());
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        } else if (Objects.equals(data.getCommentDepth(), SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_2)
                && data.getParentComment().getOwner() != null
                && !Objects.equals(data.getOwner().getId(), data.getParentComment().getOwner().getId())) {
            Notification notification = createNotification(data, notificationState, notificationKind, data.getParentComment().getOwner().getId());
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        }
    }

    @Override
    public Notification createNotification(CommentDto data, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(data, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_COMMENT_IN_MY_POST)
                || notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_REPLIED_MY_COMMENT)) {
            notification.setRefId(data.getId().toString());
        }
        return notification;
    }

    @Override
    public String getJsonMessage(CommentDto data, Notification notification) {
        CommentNotificationMessage commentNotificationMessage = new CommentNotificationMessage();
        commentNotificationMessage.setNotificationId(notification.getId());
        commentNotificationMessage.setCommentId(data.getId());
        commentNotificationMessage.setCommentContent(data.getCommentContent());
        commentNotificationMessage.setPostId(data.getPost().getId());
        commentNotificationMessage.setCommentDepth(data.getCommentDepth());
        commentNotificationMessage.setAccountId(data.getOwner().getId());
        commentNotificationMessage.setAccountName(data.getOwner().getFullName());
        commentNotificationMessage.setAccountAvatar(data.getOwner().getAvatar());
        return notificationService.convertObjectToJson(commentNotificationMessage);
    }
}
