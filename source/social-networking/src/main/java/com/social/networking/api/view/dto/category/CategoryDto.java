package com.social.networking.api.view.dto.category;

import com.social.networking.api.view.dto.InfoAdminDto;
import lombok.Data;

@Data
public class CategoryDto extends InfoAdminDto {
    private String categoryName;
    private String categoryDescription;
    private String categoryImage;
    private Integer categoryOrdering;
    private Integer categoryKind;
    private Integer parentId;
}
