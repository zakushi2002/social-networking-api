package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Notification;
import com.social.networking.api.model.criteria.NotificationCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.view.dto.ApiMessageDto;
import com.social.networking.api.view.dto.ErrorCode;
import com.social.networking.api.view.dto.ResponseListDto;
import com.social.networking.api.view.dto.notification.NotificationDto;
import com.social.networking.api.view.form.notification.CreateNotificationForm;
import com.social.networking.api.view.form.notification.SeenNotificationForm;
import com.social.networking.api.view.mapper.NotificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/notification")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class NotificationController extends BaseController {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    AccountRepository accountRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> createNotification(@Valid @RequestBody CreateNotificationForm createNotificationForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account accountFrom = accountRepository.findById(getCurrentUser()).orElse(null);
        if (accountFrom == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found!");
            return apiMessageDto;
        }
        Account accountTo = accountRepository.findById(createNotificationForm.getAccountToId()).orElse(null);
        if (accountTo == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found!");
            return apiMessageDto;
        }
        Notification notification = notificationMapper.fromCreateNotificationFormToEntity(createNotificationForm);
        notification.setAccountFrom(accountFrom);
        notification.setAccountTo(accountTo);
        notification.setStatus(SocialNetworkingConstant.STATUS_PENDING);
        notificationRepository.save(notification);
        apiMessageDto.setData(notification.getId());
        return apiMessageDto;
    }

    @PutMapping(value = "/seen", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<ResponseListDto<NotificationDto>> seenNotification(@Valid @RequestBody SeenNotificationForm seenNotificationForm, BindingResult bindingResult, Pageable pageable) {
        ApiMessageDto<ResponseListDto<NotificationDto>> apiMessageDto = new ApiMessageDto<>();
        List<Notification> notificationList = new ArrayList<>();
        for (Long notificationId : seenNotificationForm.getSeenNotificationIds()) {
            Notification notification = notificationRepository.findById(notificationId).orElse(null);
            if (notification != null && notification.getAccountTo().getId().equals(getCurrentUser()) && notification.getStatus().equals(SocialNetworkingConstant.STATUS_PENDING)) {
                notification.setStatus(SocialNetworkingConstant.STATUS_SEEN);
                notificationList.add(notification);
            }
        }
        notificationRepository.saveAll(notificationList);
        Page<Notification> notificationPage = notificationRepository.findAllByAccountToIdOrderByCreatedDateDesc(getCurrentUser(), pageable);
        ResponseListDto<NotificationDto> responseListDto = new ResponseListDto(notificationMapper.fromEntityToNotificationDtoList(notificationPage.getContent()), notificationPage.getTotalElements(), notificationPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<NotificationDto>> getMyNotification(@Valid NotificationCriteria notificationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<NotificationDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Notification> notificationPage = notificationRepository.findAll(notificationCriteria.getSpecification(), pageable);
        ResponseListDto<NotificationDto> responseListDto = new ResponseListDto(notificationMapper.fromEntityToNotificationDtoList(notificationPage.getContent()), notificationPage.getTotalElements(), notificationPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get notification list successfully!");
        return apiMessageDto;
    }
}
