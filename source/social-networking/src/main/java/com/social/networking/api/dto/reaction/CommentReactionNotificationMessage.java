package com.social.networking.api.dto.reaction;

import lombok.Data;

@Data
public class CommentReactionNotificationMessage {
    private Long notificationId;
    private Long commentReactionId;
    private Integer reactionKind;
    private Long postId;
    private Long commentId;
    private String commentContent;
    private Long accountId;
    private String accountName;
}
