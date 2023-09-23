package com.social.networking.api.view.mapper;

import com.social.networking.api.model.Permission;
import com.social.networking.api.view.dto.permission.PermissionDto;
import com.social.networking.api.view.form.permission.CreatePermissionForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "showMenu", target = "showMenu")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "nameGroup", target = "nameGroup")
    @Mapping(source = "code", target = "permissionCode")
    @BeanMapping(ignoreByDefault = true)
    Permission fromCreatePermissionFormToEntity(CreatePermissionForm createPermissionForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "showMenu", target = "showMenu")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "nameGroup", target = "nameGroup")
    @Mapping(source = "permissionCode", target = "code")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPermissionDto")
    PermissionDto fromEntityToPermissionDto(Permission permission);

    @IterableMapping(elementTargetType = PermissionDto.class, qualifiedByName = "fromEntityToPermissionDto")
    @Named("fromEntityToPermissionDtoList")
    List<PermissionDto> fromEntityToPermissionDtoList(List<Permission> permissions);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "nameGroup", target = "nameGroup")
    @Mapping(source = "permissionCode", target = "code")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPermissionDtoShort")
    PermissionDto fromEntityToPermissionDtoShort(Permission permission);

    @IterableMapping(elementTargetType = PermissionDto.class, qualifiedByName = "fromEntityToPermissionDtoShort")
    @Named("fromEntityToPermissionDtoShortList")
    List<PermissionDto> fromEntityToPermissionDtoShortList(List<Permission> permissions);
}
