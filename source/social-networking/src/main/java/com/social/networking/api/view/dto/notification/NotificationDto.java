package com.social.networking.api.view.dto.notification;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.notification.announcement.AnnouncementDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class NotificationDto extends InfoAdminDto {
    @ApiModelProperty(name = "content")
    private String content;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "objectId")
    private Long objectId;
    @ApiModelProperty(name = "announcements")
    private List<AnnouncementDto> announcements;
}
