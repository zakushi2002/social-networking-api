package com.social.networking.api.controller;

import com.social.networking.api.model.Account;
import com.social.networking.api.model.Relationship;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.RelationshipRepository;
import com.social.networking.api.view.dto.ApiMessageDto;
import com.social.networking.api.view.dto.ErrorCode;
import com.social.networking.api.view.dto.ResponseListDto;
import com.social.networking.api.view.dto.relationship.RelationshipDto;
import com.social.networking.api.view.form.relationship.CreateRelationshipForm;
import com.social.networking.api.view.mapper.RelationshipMapper;
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

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_C')")
    @Transactional
    public ApiMessageDto<Long> follow(@Valid @RequestBody CreateRelationshipForm createRelationshipForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(createRelationshipForm.getAccountId()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account not found!");
            return apiMessageDto;
        }
        Account follower = accountRepository.findById(getCurrentUser()).orElse(null);
        if (follower == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Follower is not exist!");
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

    @GetMapping(value = "/list-follower", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_FROM_L')")
    public ApiMessageDto<ResponseListDto<RelationshipDto>> listFollower(Pageable pageable) {
        ApiMessageDto<ResponseListDto<RelationshipDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Relationship> followers = relationshipRepository.findAllByAccountId(getCurrentUser(), pageable);
        ResponseListDto<RelationshipDto> responseListDto = new ResponseListDto(relationshipMapper.viewMyFollowerList(followers.getContent()), followers.getTotalElements(), followers.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get follower list successfully!");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-following", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FL_TO_L')")
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
}
