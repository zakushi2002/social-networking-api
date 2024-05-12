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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    /**
     * This method is used to create a predicate that filters out duplicate objects based on multiple key extractors.
     * It uses a ConcurrentHashMap to store the keys of the objects that have already been seen.
     *
     * @param <T>           The type of the objects to be filtered.
     * @param keyExtractors Functions that extract the keys from the objects.
     * @return A predicate that filters out duplicate objects based on the extracted keys.
     */
    @SafeVarargs
    private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        // A ConcurrentHashMap is used to store the keys of the objects that have already been seen.
        // This allows for efficient and thread-safe checking of duplicate keys.
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
        // The returned predicate checks if the keys of the given object have already been seen.
        // If they have, the predicate returns false, indicating that the object is a duplicate.
        // If they have not, the predicate returns true, indicating that the object is not a duplicate.
        return t -> {
            // Extract the keys from the object using the provided key extractors.

            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());
            // Check if the keys have already been seen.
            // If they have, return false to indicate a duplicate.
            // If they have not, add the keys to the seen map and return true to indicate a non-duplicate.
            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }
}
