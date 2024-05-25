package com.social.networking.api.dto.post;

import lombok.Data;

@Data
public class PostNotificationMessage {
    private Long notificationId;
    private Long postId;
    private String postTitle;
    private Long accountId;
    private String accountName;
    private String accountAvatar;
    private Long communityId;
    private String communityName;
    private String communityImage;
}
