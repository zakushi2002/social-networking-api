package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.BadRequestException;
import com.social.networking.api.exception.NotFoundException;
import com.social.networking.api.exception.UnauthorizationException;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Category;
import com.social.networking.api.model.ExpertProfile;
import com.social.networking.api.model.Group;
import com.social.networking.api.model.criteria.ExpertProfileCriteria;
import com.social.networking.api.repository.*;
import com.social.networking.api.service.SocialNetworkingApiService;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.profile.expert.ExpertProfileDto;
import com.social.networking.api.form.profile.expert.CreateExpertAccountForm;
import com.social.networking.api.form.profile.expert.UpdateExpertAccountForm;
import com.social.networking.api.mapper.AccountMapper;
import com.social.networking.api.mapper.ExpertProfileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/expert-account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ExpertProfileController extends BaseController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    ExpertProfileRepository expertProfileRepository;
    @Autowired
    ExpertProfileMapper expertProfileMapper;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    SocialNetworkingApiService socialNetworkingApiService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EXP_C')")
    @Transactional
    public ApiMessageDto<Long> registerExpert(@Valid @RequestBody CreateExpertAccountForm createExpertAccountForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(createExpertAccountForm.getEmail());
        if (account != null) {
            throw new BadRequestException("[Expert] Email is exist!", ErrorCode.ACCOUNT_ERROR_EMAIL_EXIST);
        }
        Group group = groupRepository.findFirstByKind(SocialNetworkingConstant.ACCOUNT_KIND_EXPERT);
        if (group == null) {
            throw new NotFoundException("[Expert] Group not found!", ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        Category hospital = categoryRepository.findById(createExpertAccountForm.getHospitalId()).orElse(null);
        if (hospital == null || !hospital.getKind().equals(SocialNetworkingConstant.CATEGORY_KIND_HOSPITAL)) {
            throw new NotFoundException("[Expert] Hospital not found or not category kind hospital!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category hospitalRole = categoryRepository.findById(createExpertAccountForm.getHospitalRoleId()).orElse(null);
        if (hospitalRole == null || !hospitalRole.getKind().equals(SocialNetworkingConstant.CATEGORY_KIND_HOSPITAL_ROLE)) {
            throw new NotFoundException("[Expert] Hospital role not found or not category kind hospital role!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category academicDegree = categoryRepository.findById(createExpertAccountForm.getAcademicDegreeId()).orElse(null);
        if (academicDegree == null || !academicDegree.getKind().equals(SocialNetworkingConstant.CATEGORY_KIND_ACADEMIC_DEGREE)) {
            throw new NotFoundException("[Expert] Academic degree not found or not category kind academic degree!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category department = categoryRepository.findById(createExpertAccountForm.getDepartmentId()).orElse(null);
        if (department == null || !department.getKind().equals(SocialNetworkingConstant.CATEGORY_KIND_DEPARTMENT)) {
            throw new NotFoundException("[Expert] Department not found or not category kind department!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        account = accountMapper.fromCreateExpertAccountFormToEntity(createExpertAccountForm);
        account.setGroup(group);
        account.setPassword(passwordEncoder.encode(createExpertAccountForm.getPassword()));
        account.setKind(SocialNetworkingConstant.ACCOUNT_KIND_EXPERT);
        if (createExpertAccountForm.getAvatarPath() != null && !createExpertAccountForm.getAvatarPath().trim().isEmpty()) {
            account.setAvatarPath(createExpertAccountForm.getAvatarPath().trim());
        }
        accountRepository.save(account);
        ExpertProfile expertProfile = expertProfileMapper.fromCreateExpertAccountFormToEntity(createExpertAccountForm);
        expertProfile.setHospital(hospital);
        expertProfile.setHospitalRole(hospitalRole);
        expertProfile.setAcademicDegree(academicDegree);
        expertProfile.setDepartment(department);
        expertProfile.setAccount(account);
        expertProfileRepository.save(expertProfile);
        apiMessageDto.setData(expertProfile.getId());
        apiMessageDto.setMessage("Register expert success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ExpertProfileDto> getExpertProfile() {
        ApiMessageDto<ExpertProfileDto> apiMessageDto = new ApiMessageDto<>();
        ExpertProfile expertProfile = expertProfileRepository.findById(getCurrentUser()).orElse(null);
        if (expertProfile == null) {
            throw new NotFoundException("[Expert] Expert profile not found!", ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(expertProfileMapper.fromEntityToDtoForClient(expertProfile));
        apiMessageDto.setMessage("My profile success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EXP_V')")
    public ApiMessageDto<ExpertProfileDto> getExpertProfile(@PathVariable("id") Long id) {
        ApiMessageDto<ExpertProfileDto> apiMessageDto = new ApiMessageDto<>();
        ExpertProfile expertProfile = expertProfileRepository.findById(id).orElse(null);
        if (expertProfile == null) {
            throw new NotFoundException("[Expert] Expert profile not found!", ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(expertProfileMapper.fromEntityToDtoForClient(expertProfile));
        apiMessageDto.setMessage("Get expert profile success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> updateExpertProfile(@Valid @RequestBody UpdateExpertAccountForm updateExpertAccountForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        ExpertProfile expertProfile = expertProfileRepository.findById(getCurrentUser()).orElse(null);
        if (expertProfile == null) {
            throw new NotFoundException("[Expert] Expert profile not found!", ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
        }
        if (updateExpertAccountForm.getPhone() != null && updateExpertAccountForm.getPhone().trim().isEmpty()) {
            throw new BadRequestException("[Expert] Phone is empty!", ErrorCode.EXPERT_PROFILE_ERROR_PHONE_EMPTY);
        }
        Category hospital = categoryRepository.findById(updateExpertAccountForm.getHospitalId()).orElse(null);
        if (hospital == null || !hospital.getKind().equals(SocialNetworkingConstant.CATEGORY_KIND_HOSPITAL)) {
            throw new NotFoundException("[Expert] Hospital not found or not category kind hospital!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category hospitalRole = categoryRepository.findById(updateExpertAccountForm.getHospitalRoleId()).orElse(null);
        if (hospitalRole == null || !hospitalRole.getKind().equals(SocialNetworkingConstant.CATEGORY_KIND_HOSPITAL_ROLE)) {
            throw new NotFoundException("[Expert] Hospital role not found or not category kind hospital role!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category academicDegree = categoryRepository.findById(updateExpertAccountForm.getAcademicDegreeId()).orElse(null);
        if (academicDegree == null || !academicDegree.getKind().equals(SocialNetworkingConstant.CATEGORY_KIND_ACADEMIC_DEGREE)) {
            throw new NotFoundException("[Expert] Academic degree not found or not category kind academic degree!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        Category department = categoryRepository.findById(updateExpertAccountForm.getDepartmentId()).orElse(null);
        if (department == null || !department.getKind().equals(SocialNetworkingConstant.CATEGORY_KIND_DEPARTMENT)) {
            throw new NotFoundException("[Expert] Department not found or not category kind department!", ErrorCode.CATEGORY_ERROR_NOT_FOUND);
        }
        if (updateExpertAccountForm.getAvatarPath() != null
                && !updateExpertAccountForm.getAvatarPath().trim().isEmpty()
                && !updateExpertAccountForm.getAvatarPath().equals(expertProfile.getAccount().getAvatarPath())) {
            socialNetworkingApiService.deleteFileS3ByLink(expertProfile.getAccount().getAvatarPath());
            expertProfile.getAccount().setAvatarPath(updateExpertAccountForm.getAvatarPath().trim());
        }
        accountRepository.save(expertProfile.getAccount());
        expertProfileMapper.mappingUpdateExpertAccountFormToEntity(updateExpertAccountForm, expertProfile);
        expertProfile.setHospital(hospital);
        expertProfile.setHospitalRole(hospitalRole);
        expertProfile.setAcademicDegree(academicDegree);
        expertProfile.setDepartment(department);
        expertProfileRepository.save(expertProfile);
        apiMessageDto.setData(expertProfile.getId());
        apiMessageDto.setMessage("Update expert profile success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EXP_L')")
    public ApiMessageDto<ResponseListDto<ExpertProfileDto>> listExpertProfileForServer(ExpertProfileCriteria expertProfileCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ExpertProfileDto>> apiMessageDto = new ApiMessageDto<>();
        Page<ExpertProfile> page = expertProfileRepository.findAll(expertProfileCriteria.getSpecification(), pageable);
        ResponseListDto<ExpertProfileDto> responseListDto = new ResponseListDto(expertProfileMapper.fromEntityListToDtoListForClient(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List expert profile success.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EXP_D')")
    @Transactional
    public ApiMessageDto<Long> deleteExpertAccount(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("[Expert] Not allow to delete expert account!");
        }
        ExpertProfile expertProfile = expertProfileRepository.findById(id).orElse(null);
        if (expertProfile == null) {
            throw new NotFoundException("[Expert] Expert profile not found!", ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
        }
        socialNetworkingApiService.deleteFileS3ByLink(expertProfile.getAccount().getAvatarPath());
        postRepository.deleteAllByAccountId(id);
        expertProfileRepository.deleteById(id);
        accountRepository.deleteById(id);
        apiMessageDto.setMessage("Delete expert account success.");
        return apiMessageDto;
    }
}
