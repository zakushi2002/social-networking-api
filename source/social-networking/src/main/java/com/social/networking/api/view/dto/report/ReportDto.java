package com.social.networking.api.view.dto.report;

import com.social.networking.api.view.dto.InfoAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportDto extends InfoAdminDto {
    @ApiModelProperty(name = "content")
    private String content;
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "objectId")
    private Long objectId;
    @ApiModelProperty(name = "quantity")
    private Integer quantity;
}
