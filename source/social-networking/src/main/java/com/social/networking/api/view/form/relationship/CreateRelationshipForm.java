package com.social.networking.api.view.form.relationship;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateRelationshipForm {
    @ApiModelProperty(name = "accountId", required = true)
    @NotNull(message = "accountId cannot be null!")
    private Long accountId;
}
