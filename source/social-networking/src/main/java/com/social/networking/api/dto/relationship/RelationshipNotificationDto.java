package com.social.networking.api.dto.relationship;

import lombok.Data;

@Data
public class RelationshipNotificationDto {
    private Long notificationId;
    private Long relationshipId;
    private Long userFollowingId; // user who is following
    private String userFollowingName;
    private Long userFollowedId; // user who is followed
}
