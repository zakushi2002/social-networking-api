package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Announcement;
import com.social.networking.api.model.Notification;
import com.social.networking.api.model.criteria.NotificationCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.AnnouncementRepository;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.notification.NotificationDto;
import com.social.networking.api.form.notification.CreateNotificationForm;
import com.social.networking.api.form.notification.annoucement.CreateAnnouncementForm;
import com.social.networking.api.mapper.AnnouncementMapper;
import com.social.networking.api.mapper.NotificationMapper;
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
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    AnnouncementMapper announcementMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<NotificationDto> createNotification(@Valid @RequestBody CreateNotificationForm createNotificationForm, BindingResult bindingResult) {
        ApiMessageDto<NotificationDto> apiMessageDto = new ApiMessageDto<>();
        Notification notification = notificationMapper.fromCreateNotificationFormToEntity(createNotificationForm);
        notificationRepository.save(notification);
        apiMessageDto.setData(notificationMapper.fromEntityToNotificationDto(notification));
        return apiMessageDto;
    }

    @PostMapping(value = "/announce", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<NotificationDto> announceNotification(@Valid @RequestBody CreateAnnouncementForm createAnnouncementForm, BindingResult bindingResult) {
        ApiMessageDto<NotificationDto> apiMessageDto = new ApiMessageDto<>();
        Notification notification = notificationRepository.findById(createAnnouncementForm.getNotificationId()).orElse(null);
        if (notification == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.NOTIFICATION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Notification not found!");
            return apiMessageDto;
        }
        List<Announcement> receiverList = new ArrayList<>();
        for (Long receiverId : createAnnouncementForm.getReceivers()) {
            Account receiver = accountRepository.findById(receiverId).orElse(null);
            if (receiver != null) {
                Announcement announcement = new Announcement();
                announcement.setNotification(notification);
                announcement.setAccount(receiver);
                announcement.setStatus(SocialNetworkingConstant.STATUS_PENDING);
                receiverList.add(announcement);
            }
        }
        announcementRepository.saveAll(receiverList);
        // notification.setAnnouncements(receiverList);
        NotificationDto notificationDto = notificationMapper.fromEntityToNotificationDtoWithAnnouncements(notification);
        notificationDto.setAnnouncements(announcementMapper.fromEntityToDtoList(receiverList));
        apiMessageDto.setData(notificationDto);
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<NotificationDto>> listNotification(@Valid NotificationCriteria notificationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<NotificationDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Notification> notificationPage = notificationRepository.findAll(notificationCriteria.getSpecification(getCurrentUser()), pageable);
        ResponseListDto<NotificationDto> responseListDto = new ResponseListDto(notificationMapper.fromEntityToNotificationDtoList(notificationPage.getContent()), notificationPage.getTotalElements(), notificationPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get notification list successfully!");
        return apiMessageDto;
    }
}
