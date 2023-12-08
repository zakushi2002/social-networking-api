package com.social.networking.api.view.form.notification;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SeenNotificationForm {
    @ApiModelProperty(name = "seenNotificationIds", example = "[1, 2, 3]", required = true)
    @NotNull(message = "announcementIds is required!")
    private Long[] announcementIds;
}
