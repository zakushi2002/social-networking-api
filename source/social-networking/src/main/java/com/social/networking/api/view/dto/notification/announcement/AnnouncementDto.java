package com.social.networking.api.view.dto.notification.announcement;

import com.social.networking.api.view.dto.InfoAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnnouncementDto extends InfoAdminDto {
    @ApiModelProperty(name = "accountId")
    private Long accountId;
}
