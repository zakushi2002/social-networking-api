package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.relationship.RelationshipNotificationDto;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Notification;
import com.social.networking.api.model.Relationship;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.NotificationRepository;
import com.social.networking.api.repository.RelationshipRepository;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.relationship.RelationshipDto;
import com.social.networking.api.form.relationship.CreateRelationshipForm;
import com.social.networking.api.mapper.RelationshipMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/relationship")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class RelationshipController extends BaseController {
    @Autowired
    RelationshipRepository relationshipRepository;
    @Autowired
    RelationshipMapper relationshipMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;


    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_C')")
    @Transactional
    public ApiMessageDto<Long> follow(@Valid @RequestBody CreateRelationshipForm createRelationshipForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(createRelationshipForm.getAccountId()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account (whom the current user wants to follow) not found!");
            return apiMessageDto;
        }
        Account follower = accountRepository.findById(getCurrentUser()).orElse(null);
        if (follower == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Follower (current user) not found!");
            return apiMessageDto;
        }
        if (account.getId().equals(follower.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.RELATIONSHIP_ERROR_NOT_FOLLOW_YOURSELF);
            apiMessageDto.setMessage("You cannot follow yourself!");
            return apiMessageDto;
        }
        Relationship relationship = relationshipRepository.findByAccountIdAndFollowerId(createRelationshipForm.getAccountId(), getCurrentUser()).orElse(null);
        if (relationship != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.RELATIONSHIP_ERROR_ALREADY_FOLLOWED);
            apiMessageDto.setMessage("You already followed this account!");
            return apiMessageDto;
        }
        relationship = new Relationship();
        relationship.setAccount(account);
        relationship.setFollower(follower);
        relationshipRepository.save(relationship);
        createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, relationship, SocialNetworkingConstant.NOTIFICATION_KIND_NEW_FOLLOWER);
        apiMessageDto.setMessage("Followed successfully!");
        apiMessageDto.setData(relationship.getId());
        return apiMessageDto;
    }

    @DeleteMapping(value = "/unfollow/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_D')")
    @Transactional
    public ApiMessageDto<Long> unfollow(@PathVariable("accountId") Long accountId) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found!");
            return apiMessageDto;
        }
        Relationship relationship = relationshipRepository.findByAccountIdAndFollowerId(accountId, getCurrentUser()).orElse(null);
        if (relationship == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.RELATIONSHIP_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("You are not following this account!");
            return apiMessageDto;
        }
        relationshipRepository.deleteById(relationship.getId());
        apiMessageDto.setMessage("Unfollowed successfully!");
        apiMessageDto.setData(relationship.getId());
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_D')")
    @Transactional
    public ApiMessageDto<Long> deleteRelationship(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Relationship relationship = relationshipRepository.findById(id).orElse(null);
        if (relationship == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.RELATIONSHIP_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Relationship not found!");
            return apiMessageDto;
        }
        if (!relationship.getFollower().getId().equals(getCurrentUser())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.RELATIONSHIP_ERROR_NO_OWNERSHIP);
            apiMessageDto.setMessage("You are not owner of this relationship!");
            return apiMessageDto;
        }
        relationshipRepository.deleteById(id);
        apiMessageDto.setMessage("Deleted successfully!");
        apiMessageDto.setData(relationship.getId());
        return apiMessageDto;
    }

    /**
     * Returns a list of accounts that the current user is following.
     *
     * @param pageable pagination information
     * @return a list of relationships
     */
    @GetMapping(value = "/list-follower", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<RelationshipDto>> listFollower(Pageable pageable) {
        ApiMessageDto<ResponseListDto<RelationshipDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Relationship> followers = relationshipRepository.findAllByAccountId(getCurrentUser(), pageable);
        ResponseListDto<RelationshipDto> responseListDto = new ResponseListDto(relationshipMapper.viewMyFollowerList(followers.getContent()), followers.getTotalElements(), followers.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get follower list successfully!");
        return apiMessageDto;
    }

    /**
     * Returns a list of accounts that are following the current user.
     *
     * @param pageable pagination information
     * @return a list of relationships
     */
    @GetMapping(value = "/list-following", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<RelationshipDto>> listFollowing(Pageable pageable) {
        ApiMessageDto<ResponseListDto<RelationshipDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Relationship> followings = relationshipRepository.findAllByFollowerId(getCurrentUser(), pageable);
        ResponseListDto<RelationshipDto> responseListDto = new ResponseListDto(relationshipMapper.viewMyFollowingList(followings.getContent()), followings.getTotalElements(), followings.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get following list successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-follower-by-account-id/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_FROM_L')")
    public ApiMessageDto<ResponseListDto<RelationshipDto>> listFollower(@PathVariable("accountId") Long accountId, Pageable pageable) {
        ApiMessageDto<ResponseListDto<RelationshipDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Relationship> followers = relationshipRepository.findAllByAccountId(accountId, pageable);
        ResponseListDto<RelationshipDto> responseListDto = new ResponseListDto(relationshipMapper.viewMyFollowerList(followers.getContent()), followers.getTotalElements(), followers.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get follower list successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-following-by-account-id/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_TO_L')")
    public ApiMessageDto<ResponseListDto<RelationshipDto>> listFollowing(@PathVariable("accountId") Long accountId, Pageable pageable) {
        ApiMessageDto<ResponseListDto<RelationshipDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Relationship> followings = relationshipRepository.findAllByFollowerId(accountId, pageable);
        ResponseListDto<RelationshipDto> responseListDto = new ResponseListDto(relationshipMapper.viewMyFollowingList(followings.getContent()), followings.getTotalElements(), followings.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get following list successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_V')")
    public ApiMessageDto<RelationshipDto> getRelationship(@PathVariable("id") Long id) {
        ApiMessageDto<RelationshipDto> apiMessageDto = new ApiMessageDto<>();
        Relationship relationship = relationshipRepository.findById(id).orElse(null);
        if (relationship == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.RELATIONSHIP_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Relationship not found!");
            return apiMessageDto;
        }
        RelationshipDto relationshipDto = relationshipMapper.fromEntityToRelationshipDto(relationship);
        apiMessageDto.setData(relationshipDto);
        apiMessageDto.setMessage("Get relationship successfully!");
        return apiMessageDto;
    }

    /**
     * Creates a JSON message for the given relationship and notification.
     *
     * @param relationship the relationship for which to create the message
     * @param notification the notification for which to create the message
     * @return the JSON message
     */
    private String getJsonMessage(Relationship relationship, Notification notification) {
        RelationshipNotificationDto relationshipNotificationDto = new RelationshipNotificationDto();
        relationshipNotificationDto.setNotificationId(notification.getId());
        relationshipNotificationDto.setRelationshipId(relationship.getId());
        relationshipNotificationDto.setUserFollowingId(relationship.getFollower().getId());
        relationshipNotificationDto.setUserFollowingName(relationship.getFollower().getFullName());
        relationshipNotificationDto.setUserFollowedId(relationship.getAccount().getId());
        return notificationService.convertObjectToJson(relationshipNotificationDto);
    }

    /**
     * Creates a notification for the given relationship and notification kind.
     *
     * @param relationship      the relationship for which to create the notification
     * @param notificationState the state of the notification
     * @param notificationKind  the kind of the notification to create
     * @param accountId         the ID of the account for which to create the notification
     * @return the created notification
     */
    private Notification createNotification(Relationship relationship, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(relationship, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_NEW_FOLLOWER)) {
            notificationRepository.deleteAllByIdUserAndKindAndRefId(accountId, SocialNetworkingConstant.NOTIFICATION_KIND_NEW_FOLLOWER, relationship.getId().toString());
            notification.setRefId(relationship.getId().toString());
        }
        return notification;
    }

    /**
     * Creates a notification and sends a message for the given relationship and notification kind.
     *
     * @param notificationState the state of the notification
     * @param relationship      the relationship for which to create the notification
     * @param notificationKind  the kind of the notification to create
     */
    private void createNotificationAndSendMessage(Integer notificationState, Relationship relationship, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        if (relationship.getAccount() != null) {
            // Create notification for the account that is being followed
            Notification notification = createNotification(relationship, notificationState, notificationKind, relationship.getAccount().getId());
            notifications.add(notification);
        }
        // Save the notifications to the database
        notificationRepository.saveAll(notifications);
        // Send the notifications to the message broker
        for (Notification notification : notifications) {
            notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
        }
    }
}
