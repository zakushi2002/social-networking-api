package com.social.networking.api.message.comment;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.comment.CommentDto;
import com.social.networking.api.dto.comment.TaggedCommentNotificationMessage;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.Notification;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaggedCommentMessage implements MessageService<CommentDto> {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotificationAndSendMessage(Integer notificationState, CommentDto data, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        if (Objects.equals(data.getCommentDepth(), SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_1)
                && Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_COMMENT_IN_MY_POST)
                && data.getOwnerIdOfPost() != null
                && !Objects.equals(data.getOwnerIdOfPost(), data.getOwner().getId())) {
            // Notify the owner of the post
            Notification notification = createNotification(data, notificationState, notificationKind, data.getOwnerIdOfPost());
            notifications.add(notification);
        } else if (Objects.equals(data.getCommentDepth(), SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_2)
                && Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_REPLIED_MY_COMMENT)
                && data.getParentComment().getOwner() != null) {
            // Notify the owner of the parent comment
            Notification notification = createNotification(data, notificationState, notificationKind, data.getParentComment().getOwner().getId());
            notifications.add(notification);
        } else if (Objects.equals(notificationKind, SocialNetworkingConstant.NOTIFICATION_KIND_TAGGED_IN_COMMENT)) {
            for (Long taggedAccountId : data.getTaggedAccountIds()) {
                Notification notification = createNotification(data, notificationState, notificationKind, taggedAccountId);
                notifications.add(notification);
            }
        }
        // Sends a message for each notification
        for (Notification notification : notifications) {
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
                || notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_REPLIED_MY_COMMENT)
                || notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_TAGGED_IN_COMMENT)) {
            notification.setRefId(data.getId().toString());
        }
        return notification;
    }

    @Override
    public String getJsonMessage(CommentDto data, Notification notification) {
        TaggedCommentNotificationMessage taggedCommentNotificationMessage = new TaggedCommentNotificationMessage();
        taggedCommentNotificationMessage.setNotificationId(notification.getId());
        taggedCommentNotificationMessage.setCommentId(data.getId());
        taggedCommentNotificationMessage.setCommentContent(data.getCommentContent());
        taggedCommentNotificationMessage.setPostId(data.getPost().getId());
        taggedCommentNotificationMessage.setCommentDepth(data.getCommentDepth());
        taggedCommentNotificationMessage.setAccountId(data.getOwner().getId());
        taggedCommentNotificationMessage.setAccountName(data.getOwner().getFullName());
        taggedCommentNotificationMessage.setAccountAvatar(data.getOwner().getAvatar());
        taggedCommentNotificationMessage.setTaggedAccountIds(data.getTaggedAccountIds());
        return notificationService.convertObjectToJson(taggedCommentNotificationMessage);
    }
}
