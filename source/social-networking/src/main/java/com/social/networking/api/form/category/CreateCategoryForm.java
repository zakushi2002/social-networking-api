package com.social.networking.api.form.category;

import com.social.networking.api.validation.CategoryKind;
import com.social.networking.api.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryForm {
    @ApiModelProperty(name = "categoryName", required = true)
    @NotEmpty(message = "categoryName cannot be null")
    private String categoryName;
    @ApiModelProperty(name = "categoryDescription", required = true)
    @NotEmpty(message = "categoryDescription cannot be null")
    private String categoryDescription;
    @ApiModelProperty(name = "categoryImage")
    private String categoryImage;
    @ApiModelProperty(name = "categoryOrdering")
    private Integer categoryOrdering;
    @CategoryKind
    @ApiModelProperty(name = "categoryKind", required = true, notes = "1: Hospital, 2: Hospital Role, 3: Department, 4: Academic Degree, 5: Community, 6: Topic")
    @NotNull(message = "categoryKind cannot be null")
    private Integer categoryKind;
    @ApiModelProperty(name = "parentId")
    private Long parentId;
    @ApiModelProperty(name = "status", required = true, notes = "1: active, 0: pending, -1: lock, -2: delete, -3: restrict")
    @NotNull(message = "status can not be null")
    @Status
    private Integer status;
}
