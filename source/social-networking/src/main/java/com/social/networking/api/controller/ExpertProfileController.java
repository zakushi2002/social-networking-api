package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.UnauthorizationException;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.ExpertProfile;
import com.social.networking.api.model.Group;
import com.social.networking.api.model.criteria.ExpertProfileCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.ExpertProfileRepository;
import com.social.networking.api.repository.GroupRepository;
import com.social.networking.api.view.dto.ApiMessageDto;
import com.social.networking.api.view.dto.ErrorCode;
import com.social.networking.api.view.dto.ResponseListDto;
import com.social.networking.api.view.dto.profile.expert.ExpertProfileDto;
import com.social.networking.api.view.form.profile.expert.CreateExpertAccountForm;
import com.social.networking.api.view.form.profile.expert.UpdateExpertAccountForm;
import com.social.networking.api.view.mapper.AccountMapper;
import com.social.networking.api.view.mapper.ExpertProfileMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EXP_C')")
    @Transactional
    public ApiMessageDto<Long> registerExpert(@Valid @RequestBody CreateExpertAccountForm createExpertAccountForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto();
        Account account = accountRepository.findAccountByEmail(createExpertAccountForm.getEmail());
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode("ACCOUNT_ERROR_EMAIL_EXIST");
            apiMessageDto.setMessage("Email is exist");
            return apiMessageDto;
        }
        Group group = groupRepository.findFirstByKind(SocialNetworkingConstant.ACCOUNT_KIND_EXPERT);
        if (group == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.GROUP_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Group not found");
            return apiMessageDto;
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
        expertProfile.setAccount(account);
        expertProfileRepository.save(expertProfile);
        apiMessageDto.setMessage("Register expert success");
        return apiMessageDto;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ExpertProfileDto> getExpertProfile() {
        ApiMessageDto<ExpertProfileDto> apiMessageDto = new ApiMessageDto<>();
        ExpertProfile expertProfile = expertProfileRepository.findById(getCurrentUser()).orElse(null);
        if (expertProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Expert profile not found");
            return apiMessageDto;
        }
        apiMessageDto.setData(expertProfileMapper.fromEntityToDtoForClient(expertProfile));
        apiMessageDto.setMessage("Get expert profile success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EXP_V')")
    public ApiMessageDto<ExpertProfileDto> getExpertProfile(@PathVariable("id") Long id) {
        ApiMessageDto<ExpertProfileDto> apiMessageDto = new ApiMessageDto<>();
        ExpertProfile expertProfile = expertProfileRepository.findById(id).orElse(null);
        if (expertProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Expert profile not found");
            return apiMessageDto;
        }
        apiMessageDto.setData(expertProfileMapper.fromEntityToDtoForClient(expertProfile));
        apiMessageDto.setMessage("Get expert profile success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> updateExpertProfile(@Valid @RequestBody UpdateExpertAccountForm updateExpertAccountForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        ExpertProfile expertProfile = expertProfileRepository.findById(getCurrentUser()).orElse(null);
        if (expertProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Expert profile not found");
            return apiMessageDto;
        }
        if (updateExpertAccountForm.getPhone() != null && updateExpertAccountForm.getPhone().trim().isEmpty()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.EXPERT_PROFILE_ERROR_PHONE_EMPTY);
            apiMessageDto.setMessage("Phone is empty");
            return apiMessageDto;
        }
        if (!passwordEncoder.matches(updateExpertAccountForm.getOldPassword(), expertProfile.getAccount().getPassword())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_WRONG_PASSWORD);
            apiMessageDto.setMessage("Old password is wrong");
            return apiMessageDto;
        }
        if (StringUtils.isNoneBlank(updateExpertAccountForm.getNewPassword())) {
            expertProfile.getAccount().setPassword(passwordEncoder.encode(updateExpertAccountForm.getNewPassword()));
        }
        if (updateExpertAccountForm.getAvatarPath() != null && !updateExpertAccountForm.getAvatarPath().trim().isEmpty()) {
            expertProfile.getAccount().setAvatarPath(updateExpertAccountForm.getAvatarPath().trim());
        }
        accountRepository.save(expertProfile.getAccount());
        expertProfileMapper.mappingUpdateExpertAccountFormToEntity(updateExpertAccountForm, expertProfile);
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
        apiMessageDto.setMessage("Get list expert profile success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('EXP_D')")
    @Transactional
    public ApiMessageDto<Long> deleteExpertAccount(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allow to delete expert account");
        }
        ExpertProfile expertProfile = expertProfileRepository.findById(id).orElse(null);
        if (expertProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Expert profile not found");
            return apiMessageDto;
        }
        expertProfileRepository.deleteById(id);
        accountRepository.deleteById(id);
        apiMessageDto.setMessage("Delete expert account success");
        return apiMessageDto;
    }
}
