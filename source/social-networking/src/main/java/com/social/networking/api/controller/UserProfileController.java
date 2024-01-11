package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.UnauthorizationException;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Group;
import com.social.networking.api.model.UserProfile;
import com.social.networking.api.model.criteria.UserProfileCriteria;
import com.social.networking.api.repository.*;
import com.social.networking.api.service.SocialNetworkingApiService;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.profile.user.UserProfileDto;
import com.social.networking.api.form.profile.user.CreateUserAccountForm;
import com.social.networking.api.form.profile.user.UpdateUserAccountForm;
import com.social.networking.api.mapper.AccountMapper;
import com.social.networking.api.mapper.UserProfileMapper;
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
@RequestMapping("/v1/user-account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserProfileController extends BaseController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    UserProfileMapper userProfileMapper;
    @Autowired
    PostRepository postRepository;
    @Autowired
    SocialNetworkingApiService socialNetworkingApiService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> registerUser(@Valid @RequestBody CreateUserAccountForm createUserAccountForm) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(createUserAccountForm.getEmail());
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_EMAIL_EXIST);
            apiMessageDto.setMessage("Email is exist");
            return apiMessageDto;
        }
        Group group = groupRepository.findFirstByKind(SocialNetworkingConstant.ACCOUNT_KIND_USER);
        if (group == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.GROUP_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Group not found");
            return apiMessageDto;
        }
        account = accountMapper.fromCreateUserAccountFormToEntity(createUserAccountForm);
        account.setKind(SocialNetworkingConstant.ACCOUNT_KIND_USER);
        account.setGroup(group);
        account.setPassword(passwordEncoder.encode(createUserAccountForm.getPassword()));
        if (createUserAccountForm.getAvatarPath() != null && !createUserAccountForm.getAvatarPath().trim().isEmpty()) {
            account.setAvatarPath(createUserAccountForm.getAvatarPath().trim());
        }
        accountRepository.save(account);
        UserProfile userProfile = userProfileMapper.fromCreateUserAccountFormToEntity(createUserAccountForm);
        userProfile.setAccount(account);
        userProfileRepository.save(userProfile);
        apiMessageDto.setMessage("Register user success");
        return apiMessageDto;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UserProfileDto> getUserProfile() {
        ApiMessageDto<UserProfileDto> apiMessageDto = new ApiMessageDto<>();
        UserProfile userProfile = userProfileRepository.findById(getCurrentUser()).orElse(null);
        if (userProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_PROFILE_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("User profile not found");
            return apiMessageDto;
        }
        apiMessageDto.setData(userProfileMapper.fromEntityToDtoForClient(userProfile));
        apiMessageDto.setMessage("Get user profile successfully");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER_V')")
    public ApiMessageDto<UserProfileDto> getUserProfile(@PathVariable("id") Long id) {
        ApiMessageDto<UserProfileDto> apiMessageDto = new ApiMessageDto<>();
        UserProfile userProfile = userProfileRepository.findById(id).orElse(null);
        if (userProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_PROFILE_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("User profile not found");
            return apiMessageDto;
        }
        apiMessageDto.setData(userProfileMapper.fromEntityToDtoForClient(userProfile));
        apiMessageDto.setMessage("Get user profile successfully");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> updateUserProfile(@Valid @RequestBody UpdateUserAccountForm updateUserAccountForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        UserProfile userProfile = userProfileRepository.findById(getCurrentUser()).orElse(null);
        if (userProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_PROFILE_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("User profile not found");
            return apiMessageDto;
        }
        if (updateUserAccountForm.getPhone() != null && updateUserAccountForm.getPhone().trim().isEmpty()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_PROFILE_ERROR_PHONE_EMPTY);
            apiMessageDto.setMessage("Phone cannot be empty");
            return apiMessageDto;
        }
        if (updateUserAccountForm.getAvatarPath() != null && !updateUserAccountForm.getAvatarPath().trim().isEmpty()) {
            // socialNetworkingApiService.deleteFileS3(userProfile.getAccount().getAvatarPath());
            userProfile.getAccount().setAvatarPath(updateUserAccountForm.getAvatarPath());
        }
        accountRepository.save(userProfile.getAccount());
        userProfileMapper.mappingUpdateUserAccountFormToEntity(updateUserAccountForm, userProfile);
        userProfileRepository.save(userProfile);
        apiMessageDto.setData(userProfile.getId());
        apiMessageDto.setMessage("Update user profile successfully");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER_L')")
    public ApiMessageDto<ResponseListDto<UserProfileDto>> listUserProfileForServer(UserProfileCriteria userProfileCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<UserProfileDto>> apiMessageDto = new ApiMessageDto<>();
        Page<UserProfile> page = userProfileRepository.findAll(userProfileCriteria.getSpecification(), pageable);
        ResponseListDto<UserProfileDto> responseListDto = new ResponseListDto(userProfileMapper.fromEntityListToDtoListForServer(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list user profile success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER_D')")
    @Transactional
    public ApiMessageDto<Long> deleteUserAccount(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allow to delete user account");
        }
        UserProfile userProfile = userProfileRepository.findById(id).orElse(null);
        if (userProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_PROFILE_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("User account not found");
            return apiMessageDto;
        }
        postRepository.deleteAllByAccountId(id);
        userProfileRepository.deleteById(id);
        accountRepository.deleteById(id);
        apiMessageDto.setMessage("Delete user account success");
        return apiMessageDto;
    }
}
