package com.social.networking.api.mapper;

import com.social.networking.api.model.Category;
import com.social.networking.api.dto.category.CategoryDto;
import com.social.networking.api.form.category.CreateCategoryForm;
import com.social.networking.api.form.category.UpdateCategoryForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    @Mapping(source = "categoryName", target = "name")
    @Mapping(source = "categoryDescription", target = "description")
    @Mapping(source = "categoryImage", target = "image")
    @Mapping(source = "categoryOrdering", target = "ordering")
    @Mapping(source = "categoryKind", target = "kind")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Category fromCreateCategoryFormToEntity(CreateCategoryForm createCategoryForm);


    @Mapping(source = "categoryDescription", target = "description")
    @Mapping(source = "categoryOrdering", target = "ordering")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateCategoryFormToEntity(UpdateCategoryForm updateCategoryForm, @MappingTarget Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "description", target = "categoryDescription")
    @Mapping(source = "image", target = "categoryImage")
    @Mapping(source = "ordering", target = "categoryOrdering")
    @Mapping(source = "kind", target = "categoryKind")
    @Mapping(source = "parentCategory.id", target = "parentId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "createdDate", target = "createdDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    CategoryDto fromEntityToAdminDto(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "adminGetMapping")
    List<CategoryDto> fromEntityListToCategoryDtoList(List<Category> categories);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "image", target = "categoryImage")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminAutoCompleteMapping")
    CategoryDto fromEntityToAdminDtoAutoComplete(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "adminAutoCompleteMapping")
    List<CategoryDto> fromEntityListToCategoryDtoAutoComplete(List<Category> categories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "description", target = "categoryDescription")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToShortDto")
    CategoryDto fromEntityToShortDto(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "fromEntityToShortDto")
    @Named("fromEntityListToShortDtoList")
    List<CategoryDto> fromEntityToShortDtoList(List<Category> categories);
}