package com.social.networking.api.form.category;

import com.social.networking.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryForm {
    @ApiModelProperty(required = true)
    @NotNull(message = "id cannot be null!")
    private Long id;
    @ApiModelProperty(name = "categoryName", required = true)
    @NotEmpty(message = "categoryName cannot be null!")
    private String categoryName;
    @ApiModelProperty(name = "categoryDescription")
    private String categoryDescription;
    @ApiModelProperty(name = "categoryImage")
    private String categoryImage;
    @ApiModelProperty(name = "categoryOrdering")
    private Integer categoryOrdering;
    @ApiModelProperty(name = "status", required = true, notes = "1: active, 0: pending, -1: lock, -2: delete, -3: restrict")
    @NotNull(message = "status cannot be null!")
    @Status
    private Integer status;

}
