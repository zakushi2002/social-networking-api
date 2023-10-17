package com.social.networking.api.view.form.report;

import com.social.networking.api.view.validation.ReportKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateReportForm {
    @ApiModelProperty(name = "objectId", required = true)
    @NotNull(message = "objectId cannot be null!")
    private Long objectId;
    @ApiModelProperty(name = "kind", required = true)
    @ReportKind
    private Integer kind;
    @ApiModelProperty(name = "content", required = true)
    @NotEmpty(message = "content cannot be empty!")
    private String content;
}
