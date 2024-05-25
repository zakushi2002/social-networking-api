package com.social.networking.api.form.notification;

import com.social.networking.api.validation.NotificationKind;
import com.social.networking.api.validation.NotificationState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class UpdateNotificationForm {

    @NotNull(message = "id cant not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @NotNull(message = "idUser cant not be null")
    @ApiModelProperty(name = "idUser", required = true)
    private Long idUser;

    @ApiModelProperty(name = "refId")
    private String refId;

    @NotNull(message = "state cant not be null")
    @ApiModelProperty(name = "state", required = true)
    @NotificationState
    private Integer state;

    @ApiModelProperty(name = "kind", required = true)
    @NotificationKind
    @NotNull(message = "kind cant not be null")
    private Integer kind;

    @NotEmpty(message = "content cant not be empty")
    @ApiModelProperty(name = "content", required = true)
    private String content;

    @NotNull(message = "status cant not be null")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;

}
