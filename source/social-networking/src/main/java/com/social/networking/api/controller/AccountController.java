package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.UnauthorizationException;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.ExpertProfile;
import com.social.networking.api.model.Group;
import com.social.networking.api.model.UserProfile;
import com.social.networking.api.model.criteria.AccountCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.ExpertProfileRepository;
import com.social.networking.api.repository.GroupRepository;
import com.social.networking.api.repository.UserProfileRepository;
import com.social.networking.api.service.SocialNetworkingApiService;
import com.social.networking.api.view.dto.ApiMessageDto;
import com.social.networking.api.view.dto.ApiResponse;
import com.social.networking.api.view.dto.ErrorCode;
import com.social.networking.api.view.dto.ResponseListDto;
import com.social.networking.api.view.dto.account.AccountDto;
import com.social.networking.api.view.dto.account.AccountProfileDto;
import com.social.networking.api.view.form.account.CreateAdminForm;
import com.social.networking.api.view.form.account.UpdateAdminForm;
import com.social.networking.api.view.form.account.UpdateAdminProfileForm;
import com.social.networking.api.view.form.account.forgot.ChangePasswordForgotForm;
import com.social.networking.api.view.form.account.forgot.GetOTPForm;
import com.social.networking.api.view.form.account.forgot.OTPForm;
import com.social.networking.api.view.mapper.AccountMapper;
import com.social.networking.api.view.mapper.ExpertProfileMapper;
import com.social.networking.api.view.mapper.UserProfileMapper;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    SocialNetworkingApiService socialNetworkingApiService;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    ExpertProfileRepository expertProfileRepository;

    @PostMapping(value = "/create-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_C_AD')")
    @Transactional
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
    @Transactional
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
            // socialNetworkingApiService.deleteFileS3(account.getAvatarPath());
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

    @GetMapping(value = "/get-profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFILE_V')")
    public ApiMessageDto<AccountProfileDto> getAccountProfile(@PathVariable("id") Long id) {
        ApiMessageDto<AccountProfileDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found");
            return apiMessageDto;
        }
        if (account.getKind().equals(SocialNetworkingConstant.ACCOUNT_KIND_USER)) {
            UserProfile userProfile = userProfileRepository.findById(id).orElse(null);
            if (userProfile == null) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
                apiMessageDto.setMessage("User profile not found");
                return apiMessageDto;
            }
            apiMessageDto.setData(accountMapper.fromEntityToProfileDtoForClient(userProfile));
        } else if (account.getKind().equals(SocialNetworkingConstant.ACCOUNT_KIND_EXPERT)) {
            ExpertProfile expertProfile = expertProfileRepository.findById(id).orElse(null);
            if (expertProfile == null) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
                apiMessageDto.setMessage("Expert profile not found");
                return apiMessageDto;
            }
            apiMessageDto.setData(accountMapper.fromEntityToProfileDtoForClient(expertProfile));
        }
        apiMessageDto.setMessage("Get account profile success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete-admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_D_AD')")
    @Transactional
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
    @Transactional
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

    @PostMapping(value = "/send-otp-code", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @ApiIgnore
    public ApiMessageDto<Long> sendOTPCode(@Valid @RequestBody GetOTPForm getOTPForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(getOTPForm.getEmail());
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (account.getStatus().equals(SocialNetworkingConstant.STATUS_LOCK)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_LOCKED);
            apiMessageDto.setMessage("Account was locked");
            return apiMessageDto;
        }
        if (account.getResetPwdCode() != null && isOTPRequired(account)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_SENT_REQUEST_OTP);
            apiMessageDto.setMessage("Account was sent request OTP. Please check your email!");
            return apiMessageDto;
        }
        String otpCode = socialNetworkingApiService.getOTPForgetPassword();
        account.setResetPwdCode(otpCode);
        account.setResetPwdTime(new Date());
        accountRepository.save(account);
        Map<String, Object> variables = new HashMap<>();
        variables.put("otpCode", otpCode);
        variables.put("fullName", account.getFullName());
        socialNetworkingApiService.sendEmail(getOTPForm.getEmail(), variables, SocialNetworkingConstant.OTP_SUBJECT_EMAIL);
        apiMessageDto.setMessage("Send OTP code success");
        return apiMessageDto;
    }

    @PutMapping(value = "/check-otp-code", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @ApiIgnore
    public ApiMessageDto<String> checkOTPCode(@Valid @RequestBody OTPForm otpForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(otpForm.getEmail());
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (account.getStatus().equals(SocialNetworkingConstant.STATUS_LOCK)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_LOCKED);
            apiMessageDto.setMessage("Account was locked");
            return apiMessageDto;
        }
        if (account.getResetPwdCode() == null || account.getResetPwdCode().trim().isEmpty()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_SEND_REQUEST_OTP);
            apiMessageDto.setMessage("Account not send request OTP");
            return apiMessageDto;
        }
        if (account.getAttemptCode() != null && account.getAttemptCode() > SocialNetworkingConstant.ATTEMPT_CODE_MAX) {
            account.setStatus(SocialNetworkingConstant.STATUS_LOCK);
            accountRepository.save(account);
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_LOCKED);
            apiMessageDto.setMessage("Account is locked");
            return apiMessageDto;
        }
        if (!otpForm.getOtp().equals(account.getResetPwdCode())) {
            if (account.getAttemptCode() == null) {
                account.setAttemptCode(SocialNetworkingConstant.ATTEMPT_CODE_START);
            } else {
                account.setAttemptCode(account.getAttemptCode() + 1);
            }
            accountRepository.save(account);
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_OTP_INVALID);
            return apiMessageDto;
        }
        if (!isOTPRequired(account)) {
            account.setResetPwdCode(null);
            account.setResetPwdTime(null);
            accountRepository.save(account);
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_OTP_EXPIRED);
            return apiMessageDto;
        }
        account.setAttemptCode(null);
        account.setResetPwdCode(null);
        account.setResetPwdTime(null);
        accountRepository.save(account);
        apiMessageDto.setMessage("Check OTP code success");
        return apiMessageDto;
    }

    @PutMapping(value = "/change-password-forgot", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @ApiIgnore
    public ApiMessageDto<Long> changePasswordForgot(@Valid @RequestBody ChangePasswordForgotForm changePasswordForgotForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(changePasswordForgotForm.getEmail());
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        account.setPassword(passwordEncoder.encode(changePasswordForgotForm.getNewPassword()));
        accountRepository.save(account);
        apiMessageDto.setMessage("Change password success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-account-client", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_L_CLIENT')")
    public ApiResponse<ResponseListDto<AccountDto>> listAccountClient(AccountCriteria accountCriteria, Pageable pageable) {
        ApiResponse<ResponseListDto<AccountDto>> apiMessageDto = new ApiResponse<>();
        Page<Account> page = accountRepository.findAll(accountCriteria.getSpecification(), pageable);
        ResponseListDto<AccountDto> responseListDto = new ResponseListDto(accountMapper.fromAccountToAutoCompleteDtoWithGroupList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list account success");
        return apiMessageDto;
    }

    public boolean isOTPRequired(Account account) {
        if (account.getResetPwdCode() == null || account.getResetPwdCode().trim().isEmpty()) {
            return false;
        }
        long otpRequestedTime = account.getResetPwdTime().getTime();
        return otpRequestedTime + SocialNetworkingConstant.OTP_VALID_DURATION >= System.currentTimeMillis(); // OTP expires
    }
}
