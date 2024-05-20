package com.social.networking.api.dto.comment;

import lombok.Data;

import java.util.List;

@Data
public class TaggedCommentNotificationMessage {
    private Long notificationId;
    private Long commentId;
    private Long postId;
    private Integer commentDepth;
    private Long accountId;
    private String accountName;
    private String accountAvatar;
    private List<Long> taggedAccountIds;
}
