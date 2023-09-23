package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.UnauthorizationException;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Group;
import com.social.networking.api.model.criteria.AccountCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.GroupRepository;
import com.social.networking.api.view.dto.ApiResponse;
import com.social.networking.api.view.dto.ErrorCode;
import com.social.networking.api.view.dto.ResponseListDto;
import com.social.networking.api.view.dto.account.AccountDto;
import com.social.networking.api.view.form.account.CreateAdminForm;
import com.social.networking.api.view.form.account.UpdateAdminForm;
import com.social.networking.api.view.form.account.UpdateAdminProfileForm;
import com.social.networking.api.view.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AccountController extends BaseController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    GroupRepository groupRepository;

    @PostMapping(value = "/create-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_C_AD')")
    public ApiResponse<String> createAdmin(@Valid @RequestBody CreateAdminForm createAdminForm, BindingResult bindingResult) {
        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findAccountByEmail(createAdminForm.getEmail());
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_EMAIL_EXIST);
            apiMessageDto.setMessage("Email is exist");
            return apiMessageDto;
        }
        Group group = groupRepository.findById(createAdminForm.getGroupId()).orElse(null);
        if (group == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.GROUP_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Group not found");
            return apiMessageDto;
        }
        account = new Account();
        account.setEmail(createAdminForm.getEmail());
        account.setFullName(createAdminForm.getFullName());
        account.setGroup(group);
        account.setPassword(passwordEncoder.encode(createAdminForm.getPassword()));
        account.setKind(SocialNetworkingConstant.ACCOUNT_KIND_ADMIN);
        account.setAvatarPath(createAdminForm.getAvatarPath());
        accountRepository.save(account);
        apiMessageDto.setMessage("Create admin success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_U_AD')")
    public ApiResponse<String> updateAdmin(@Valid @RequestBody UpdateAdminForm updateAdminForm, BindingResult bindingResult) {
        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(updateAdminForm.getId()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found");
            return apiMessageDto;
        }
        if (updateAdminForm.getPassword() != null && !updateAdminForm.getPassword().trim().isEmpty()) {
            account.setPassword(passwordEncoder.encode(updateAdminForm.getPassword()));
        } else if (updateAdminForm.getPassword() != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_PASSWORD_INVALID);
            apiMessageDto.setMessage("password cannot be empty!");
            return apiMessageDto;
        }
        if (updateAdminForm.getAvatarPath() != null && !updateAdminForm.getAvatarPath().trim().isEmpty()) {
            account.setAvatarPath(updateAdminForm.getAvatarPath());
        }
        accountMapper.mappingUpdateAdminFormToAccount(updateAdminForm, account);
        accountRepository.save(account);
        apiMessageDto.setMessage("Update admin success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_V')")
    public ApiResponse<Account> getAccount(@PathVariable("id") Long id) {
        ApiResponse<Account> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found");
            return apiMessageDto;
        }
        apiMessageDto.setData(account);
        apiMessageDto.setMessage("Get account success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete-admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_D_AD')")
    public ApiResponse<String> deleteAdmin(@PathVariable("id") Long id) {
        if (!isSuperAdmin()) {
            throw new UnauthorizationException("Not allowed to delete.");
        }
        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found");
            return apiMessageDto;
        }
        accountRepository.deleteById(id);
        apiMessageDto.setMessage("Delete admin success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_L')")
    public ApiResponse<ResponseListDto<AccountDto>> listAccount(AccountCriteria accountCriteria, Pageable pageable) {
        ApiResponse<ResponseListDto<AccountDto>> apiMessageDto = new ApiResponse<>();
        Page<Account> page = accountRepository.findAll(accountCriteria.getSpecification(), pageable);
        ResponseListDto<AccountDto> responseListDto = new ResponseListDto(accountMapper.fromEntityToAccountDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list account success");
        return apiMessageDto;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<AccountDto> getProfile() {
        ApiResponse<AccountDto> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found");
            return apiMessageDto;
        }
        apiMessageDto.setData(accountMapper.fromAccountToDtoProfile(account));
        apiMessageDto.setMessage("Get profile success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-admin-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> updateProfileAdmin(@Valid @RequestBody UpdateAdminProfileForm updateAdminProfileForm, BindingResult bindingResult) {
        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found");
            return apiMessageDto;
        }
        if (!passwordEncoder.matches(updateAdminProfileForm.getOldPassword(), account.getPassword())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_WRONG_PASSWORD);
            apiMessageDto.setMessage("Old password is wrong");
            return apiMessageDto;
        }
        if (StringUtils.isNoneBlank(updateAdminProfileForm.getNewPassword())) {
            account.setPassword(passwordEncoder.encode(updateAdminProfileForm.getNewPassword()));
        }
        if (updateAdminProfileForm.getAvatarPath() != null && !updateAdminProfileForm.getAvatarPath().trim().isEmpty()) {
            account.setAvatarPath(updateAdminProfileForm.getAvatarPath());
        }
        account.setFullName(updateAdminProfileForm.getFullName());
        accountRepository.save(account);
        apiMessageDto.setMessage("Update admin profile success");
        return apiMessageDto;
    }
}
