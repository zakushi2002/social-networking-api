package com.social.networking.api.dto.notification.announcement;

import com.social.networking.api.dto.InfoAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnnouncementDto extends InfoAdminDto {
    @ApiModelProperty(name = "accountId")
    private Long accountId;
}
