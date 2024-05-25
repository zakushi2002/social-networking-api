package com.social.networking.api.dto.notification;

import lombok.Data;

import java.util.List;

@Data
public class MynotificationDto {

    private Long totalUnread;
    private List<NotificationDto> content ;
    private long totalPages;
    private long totalElements;

}
