package com.social.networking.api.controller;

import com.social.networking.api.exception.UnauthorizationException;
import com.social.networking.api.model.Group;
import com.social.networking.api.model.Permission;
import com.social.networking.api.model.criteria.PermissionCriteria;
import com.social.networking.api.repository.GroupRepository;
import com.social.networking.api.repository.PermissionRepository;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.permission.PermissionDto;
import com.social.networking.api.view.form.permission.CreatePermissionForm;
import com.social.networking.api.view.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/permission")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@ApiIgnore
public class PermissionController extends BaseController {
    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    GroupRepository groupRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PER_C')")
    @Transactional
    public ApiMessageDto<String> createPermission(@Valid @RequestBody CreatePermissionForm createPermissionForm, BindingResult bindingResult) {
        if(!isSuperAdmin()){
            throw new UnauthorizationException("Not allowed create.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Permission permission = permissionRepository.findFirstByName(createPermissionForm.getName());
        if (permission != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.PERMISSION_ERROR_NAME_EXIST);
            apiMessageDto.setMessage("Permission name is exist");
            return apiMessageDto;
        }
        permission = permissionRepository.findByPermissionCode(createPermissionForm.getCode());
        if (permission != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.PERMISSION_ERROR_CODE_EXIST);
            apiMessageDto.setMessage("Permission code is exist");
            return apiMessageDto;
        }
        permission = permissionMapper.fromCreatePermissionFormToEntity(createPermissionForm);
        permissionRepository.save(permission);
        apiMessageDto.setMessage("Create permission success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PER_L')")
    public ApiMessageDto<ResponseListDto<PermissionDto>> listPermission(PermissionCriteria permissionCriteria, @PageableDefault(size = 1000) Pageable pageable) {
        if(!isSuperAdmin()){
            throw new UnauthorizationException("Not allowed list.");
        }
        Page<Permission> page = permissionRepository.findAll(permissionCriteria.getSpecification(), pageable);
        ResponseListDto<PermissionDto> responseListDto = new ResponseListDto(permissionMapper.fromEntityToPermissionDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        ApiMessageDto<ResponseListDto<PermissionDto>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List permission success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PER_D')")
    @Transactional
    public ApiMessageDto<String> deletePermission(@PathVariable("id") Long id) {
        if(!isSuperAdmin()){
            throw new UnauthorizationException("Not allowed delete.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Permission permission = permissionRepository.findById(id).orElse(null);
        if (permission == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.PERMISSION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Permission not found");
            return apiMessageDto;
        }
        if (permission.getGroups() != null && !permission.getGroups().isEmpty()) {
            for (Group group : permission.getGroups()) {
                group.getPermissions().removeIf(p -> p.getId().equals(permission.getId()));
            }
            groupRepository.saveAll(permission.getGroups());
        }
        permissionRepository.deleteById(id);
        apiMessageDto.setMessage("Delete permission success");
        return apiMessageDto;
    }
}
