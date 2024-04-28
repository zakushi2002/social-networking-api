package com.social.networking.api.dto.notification;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.notification.announcement.AnnouncementDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class NotificationDto extends InfoAdminDto {
    @ApiModelProperty(name = "idUser")
    private Long idUser;
    @ApiModelProperty(name = "state")
    private Integer state;
    @ApiModelProperty(name = "refId")
    private String refId;
    @ApiModelProperty(name = "content")
    private String content;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "objectId")
    private Long objectId;
    @ApiModelProperty(name = "announcements")
    private List<AnnouncementDto> announcements;
    @ApiModelProperty(name = "createdBy")
    private String createdBy;
}
