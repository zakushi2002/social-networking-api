package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.notification.MynotificationDto;
import com.social.networking.api.dto.notification.NotificationDto;
import com.social.networking.api.exception.NotFoundException;
import com.social.networking.api.form.notification.ChangeStateNotification;
import com.social.networking.api.form.notification.UpdateNotificationForm;
import com.social.networking.api.mapper.NotificationMapper;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Notification;
import com.social.networking.api.model.criteria.NotificationCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v2/notification")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@Validated
public class NotificationVersion2Controller extends BaseController {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    AccountRepository accountRepository;

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> deleteNotification(@PathVariable("id") Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification == null) {
            throw new NotFoundException("[Notification Version 2] Notification not found!", ErrorCode.NOTIFICATION_ERROR_NOT_FOUND);
        }
        notificationRepository.delete(notification);
        apiMessageDto.setMessage("Delete notification success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<NotificationDto>>> listNotification(NotificationCriteria notificationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<NotificationDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<NotificationDto>> responseListDto = new ResponseListDto<>();
        Page<Notification> listNotification = notificationRepository.findAll(notificationCriteria.getSpecification(null), pageable);
        responseListDto.setContent(notificationMapper.fromEntityToNotiListDto(listNotification.getContent()));
        responseListDto.setTotalPages(listNotification.getTotalPages());
        responseListDto.setTotalElements(listNotification.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List notification success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<NotificationDto>>> listAutoComplete(NotificationCriteria notificationCriteria) {
        ApiMessageDto<ResponseListDto<List<NotificationDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<NotificationDto>> responseListDto = new ResponseListDto<>();
        notificationCriteria.setStatus(SocialNetworkingConstant.STATUS_ACTIVE);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Notification> listNotification = notificationRepository.findAll(notificationCriteria.getSpecification(null), pageable);
        responseListDto.setContent(notificationMapper.fromEntityToNotiListDtoAuto(listNotification.getContent()));
        responseListDto.setTotalPages(listNotification.getTotalPages());
        responseListDto.setTotalElements(listNotification.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Auto complete notification success.");
        return apiMessageDto;

    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('NT_V')")
    public ApiMessageDto<NotificationDto> getNotification(@PathVariable("id") Long id) {
        ApiMessageDto<NotificationDto> apiMessageDto = new ApiMessageDto<>();
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification == null) {
            throw new NotFoundException("[Notification Version 2] Notification not found!", ErrorCode.NOTIFICATION_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(notificationMapper.fromEntityToNotificatonDto(notification));
        apiMessageDto.setMessage("Get notification success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('NT_U')")
    public ApiMessageDto<String> updateNotification(@Valid @RequestBody UpdateNotificationForm updateNotificationForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Notification notification = notificationRepository.findById(updateNotificationForm.getId()).orElse(null);
        if (notification == null) {
            throw new NotFoundException("[Notification Version 2] Notification not found!", ErrorCode.NOTIFICATION_ERROR_NOT_FOUND);
        }
        Account account = accountRepository.findById(updateNotificationForm.getIdUser()).orElse(null);
        if (account == null) {
            throw new NotFoundException("[Notification Version 2] Account not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        notificationMapper.fromUpdateNotiToEntity(updateNotificationForm, notification);
        notificationRepository.save(notification);
        apiMessageDto.setMessage("Update notification success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/change-state", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('NT_CS')")
    public ApiMessageDto<String> changeStateNotification(@Valid @RequestBody ChangeStateNotification changeStateNotification, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Notification notification = notificationRepository.findById(changeStateNotification.getId()).orElse(null);
        if (notification == null) {
            throw new NotFoundException("[Notification Version 2] Notification not found!", ErrorCode.NOTIFICATION_ERROR_NOT_FOUND);
        }
        if (notification.getState().equals(SocialNetworkingConstant.NOTIFICATION_STATE_SENT)) {
            notification.setState(SocialNetworkingConstant.NOTIFICATION_STATE_READ);
        }
        notificationRepository.save(notification);
        apiMessageDto.setMessage("Change state notification success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/my-notification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<MynotificationDto> getMyNotification(@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value = "state", required = false) Integer state) {
        ApiMessageDto<MynotificationDto> apiMessageDto = new ApiMessageDto<>();
        Long userId = getCurrentUser();
        MynotificationDto mynotificationDto = new MynotificationDto();
        Page<Notification> notificationPage;
        if (state != null) {
            notificationPage = notificationRepository.findAllByIdUserAndState(userId, state, pageable);
        } else {
            notificationPage = notificationRepository.findAllByIdUser(userId, pageable);
        }
        mynotificationDto.setContent(notificationMapper.fromEntityToNotiListDto(notificationPage.getContent()));
        mynotificationDto.setTotalPages(notificationPage.getTotalPages());
        mynotificationDto.setTotalElements(notificationPage.getTotalElements());
        mynotificationDto.setTotalUnread(notificationRepository.countByIdUserAndState(userId, SocialNetworkingConstant.NOTIFICATION_STATE_SENT));
        apiMessageDto.setData(mynotificationDto);
        apiMessageDto.setMessage("Get my notification success.");
        return apiMessageDto;
    }

    @PutMapping(value = "/read-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> readAll() {
        Long userId = getCurrentUser();
        List<Notification> notifications = notificationRepository.findAllByIdUserAndState(userId, SocialNetworkingConstant.NOTIFICATION_STATE_SENT);
        notifications.forEach(notification -> notification.setState(SocialNetworkingConstant.NOTIFICATION_STATE_READ));
        notificationRepository.saveAll(notifications);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Has moved to the fully read state.");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> deleteAllNotification() {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Long userId = getCurrentUser();
        notificationRepository.deleteAllByIdUser(userId);
        apiMessageDto.setMessage("Delete all notification success.");
        return apiMessageDto;
    }
}