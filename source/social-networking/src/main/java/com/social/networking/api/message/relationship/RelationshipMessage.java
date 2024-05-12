package com.social.networking.api.message.relationship;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.relationship.RelationshipNotificationDto;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.Notification;
import com.social.networking.api.model.Relationship;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationshipMessage implements MessageService<Relationship> {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotificationAndSendMessage(Integer notificationState, Relationship data, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        if (data.getFollower() != null) {
            // Create notification for the account that is being followed
            Notification notification = createNotification(data, notificationState, notificationKind, data.getFollower().getId());
            notifications.add(notification);
        }
        // Save the notifications to the database
        notificationRepository.saveAll(notifications);
        // Send the notifications to the message broker
        for (Notification notification : notifications) {
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        }
    }

    @Override
    public Notification createNotification(Relationship data, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(data, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_NEW_FOLLOWER)) {
            notificationRepository.deleteAllByIdUserAndKindAndRefId(accountId, SocialNetworkingConstant.NOTIFICATION_KIND_NEW_FOLLOWER, data.getFollower().getId().toString());
            notification.setRefId(data.getFollower().getId().toString());
        }
        return notification;
    }

    @Override
    public String getJsonMessage(Relationship data, Notification notification) {
        RelationshipNotificationDto relationshipNotificationDto = new RelationshipNotificationDto();
        relationshipNotificationDto.setNotificationId(notification.getId());
        relationshipNotificationDto.setRelationshipId(data.getId());
        relationshipNotificationDto.setUserFollowingId(data.getFollower().getId());
        relationshipNotificationDto.setUserFollowingName(data.getFollower().getFullName());
        relationshipNotificationDto.setUserFollowingAvatar(data.getFollower().getAvatarPath());
        relationshipNotificationDto.setUserFollowedId(data.getAccount().getId());
        return notificationService.convertObjectToJson(relationshipNotificationDto);
    }
}
