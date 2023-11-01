package com.social.networking.api.view.dto.notification;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.account.AccountDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NotificationDto extends InfoAdminDto {
    @ApiModelProperty(name = "content")
    private String content;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "accountTo")
    private AccountDto accountTo;
    @ApiModelProperty(name = "accountFrom")
    private AccountDto accountFrom;
    @ApiModelProperty(name = "isRead")
    private Boolean isRead;
}
