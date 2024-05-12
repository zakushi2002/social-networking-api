package com.social.networking.api.dto.comment;

import lombok.Data;

@Data
public class CommentNotificationMessage {
    private Long notificationId;
    private Long commentId;
    private Long postId;
    private Integer commentDepth;
    private Long accountId;
    private String accountName;
    private String accountAvatar;
}
