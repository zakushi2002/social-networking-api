package com.social.networking.api.dto.reaction;

import lombok.Data;

@Data
public class PostReactionNotificationMessage {
    private Long notificationId;
    private Long postReactionId;
    private Integer reactionKind;
    private Long postId;
    private String postTitle;
    private Long accountId;
    private String accountName;
    private String accountAvatar;
}
