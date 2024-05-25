package com.social.networking.api.message.post;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.post.PostNotificationMessage;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.CommunityMember;
import com.social.networking.api.model.Notification;
import com.social.networking.api.model.Post;
import com.social.networking.api.model.Relationship;
import com.social.networking.api.repository.CommunityMemberRepository;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.repository.RelationshipRepository;
import com.social.networking.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.social.networking.api.utils.Utils.distinctByKeys;

@Service
public class PostMessage implements MessageService<Post> {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    RelationshipRepository relationshipRepository;
    @Autowired
    CommunityMemberRepository communityMemberRepository;

    @Override
    public void createNotificationAndSendMessage(Integer notificationState, Post data, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        // Adds the following accounts to the notifications list
        List<Relationship> following = relationshipRepository.findAllByAccountId(data.getAccount().getId());
        for (Relationship relationship : following) {
            Notification notification = createNotification(data, notificationState, notificationKind, relationship.getFollower().getId());
            notifications.add(notification);
        }
        List<CommunityMember> communityMembers = communityMemberRepository.findAllByCommunityId(data.getCommunity().getId());
        communityMembers = communityMembers.stream()
                .filter(cm -> !cm.getAccount().getId().equals(data.getAccount().getId()))
                .collect(Collectors.toList());
        // Adds the community members to the notifications list
        for (CommunityMember communityMember : communityMembers) {
            Notification notification = createNotification(data, notificationState, notificationKind, communityMember.getAccount().getId());
            notifications.add(notification);
        }
        // Removes the duplicate notifications from the list since the post owner and the user who follows the post owner are in the community members list.
        notifications = notifications.stream()
                .filter(distinctByKeys(Notification::getIdUser, Notification::getRefId, Notification::getKind))
                .collect(Collectors.toList());
        // Sends a message for each notification
        for (Notification notification : notifications) {
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        }
    }

    @Override
    public Notification createNotification(Post data, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(data, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_NEW_POST_OF_FOLLOWING)) {
            notificationRepository.deleteAllByIdUserAndKindAndRefId(accountId, SocialNetworkingConstant.NOTIFICATION_KIND_NEW_POST_OF_FOLLOWING, data.getId().toString());
            notification.setRefId(data.getId().toString());
        }
        return notification;
    }

    @Override
    public String getJsonMessage(Post data, Notification notification) {
        PostNotificationMessage postNotificationMessage = new PostNotificationMessage();
        postNotificationMessage.setNotificationId(notification.getId());
        postNotificationMessage.setPostId(data.getId());
        postNotificationMessage.setPostTitle(data.getTitle());
        postNotificationMessage.setAccountId(data.getAccount().getId());
        postNotificationMessage.setAccountName(data.getAccount().getFullName());
        postNotificationMessage.setAccountAvatar(data.getAccount().getAvatarPath());
        postNotificationMessage.setCommunityId(data.getCommunity().getId());
        postNotificationMessage.setCommunityName(data.getCommunity().getName());
        postNotificationMessage.setCommunityImage(data.getCommunity().getImage());
        return notificationService.convertObjectToJson(postNotificationMessage);
    }
}
