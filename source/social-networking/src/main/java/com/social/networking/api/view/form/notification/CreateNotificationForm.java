package com.social.networking.api.view.form.notification;

import com.social.networking.api.view.validation.NotificationKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateNotificationForm {
    @ApiModelProperty(name = "content", required = true)
    @NotEmpty(message = "content cannot be empty!")
    private String content;
    @ApiModelProperty(name = "kind", required = true)
    @NotificationKind
    private Integer kind;
    @ApiModelProperty(name = "objectId", required = true)
    @NotNull(message = "objectId cannot be null!")
    private Long objectId;
}
