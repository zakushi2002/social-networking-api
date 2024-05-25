package com.social.networking.api.form.notification.annoucement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAnnouncementForm {
    private Long notificationId;
    @ApiModelProperty(name = "receivers", required = true)
    @NotNull(message = "receivers cannot be null!")
    private Long[] receivers;
}
