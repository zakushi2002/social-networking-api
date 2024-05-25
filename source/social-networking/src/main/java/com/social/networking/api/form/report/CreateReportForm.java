package com.social.networking.api.form.report;

import com.social.networking.api.validation.ReportKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateReportForm {
    @ApiModelProperty(name = "objectId", required = true)
    @NotNull(message = "objectId cannot be null!")
    private Long objectId;
    @ApiModelProperty(name = "kind", required = true, notes = "1: post, 2: comment, 3: user")
    @ReportKind
    private Integer kind;
    @ApiModelProperty(name = "content", required = true)
    @NotEmpty(message = "content cannot be empty!")
    private String content;
}
