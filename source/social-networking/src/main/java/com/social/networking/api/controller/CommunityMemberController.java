package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.BadRequestException;
import com.social.networking.api.exception.NotFoundException;
import com.social.networking.api.mapper.CommunityMemberMapper;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Category;
import com.social.networking.api.model.CommunityMember;
import com.social.networking.api.model.criteria.CommunityMemberCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.CategoryRepository;
import com.social.networking.api.repository.CommunityMemberRepository;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.community.member.CommunityMemberDto;
import com.social.networking.api.form.community.member.CreateCommunityMemberForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/community-member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CommunityMemberController extends BaseController {
    @Autowired
    CommunityMemberRepository communityMemberRepository;
    @Autowired
    CommunityMemberMapper communityMemberMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> joinCommunity(@Valid @RequestBody CreateCommunityMemberForm createCommunityMemberForm) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        CommunityMember communityMember = communityMemberRepository.findByAccountIdAndCommunityId(getCurrentUser(), createCommunityMemberForm.getCommunityId()).orElse(null);
        if (communityMember != null) {
            throw new BadRequestException("[Community Member] You have joined this community!", ErrorCode.COMMUNITY_MEMBER_JOINED);
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            throw new NotFoundException("[Community Member] You are not logged in!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        Category community = categoryRepository.findByIdAndKind(createCommunityMemberForm.getCommunityId(), SocialNetworkingConstant.CATEGORY_KIND_COMMUNITY).orElse(null);
        if (community == null) {
            throw new BadRequestException("[Community Member] Not found community!", ErrorCode.CATEGORY_ERROR_NOT_COMMUNITY_KIND);
        }
        communityMember = new CommunityMember();
        communityMember.setAccount(account);
        communityMember.setCommunity(community);
        communityMember.setKind(SocialNetworkingConstant.COMMUNITY_MEMBER_KIND);
        communityMemberRepository.save(communityMember);
        apiMessageDto.setData(community.getId());
        apiMessageDto.setMessage("You have joined the " + community.getName() + " community.");
        return apiMessageDto;
    }

    @PostMapping(value = "/leave", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> leaveCommunity(@Valid @RequestBody CreateCommunityMemberForm createCommunityMemberForm) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        CommunityMember communityMember = communityMemberRepository.findByAccountIdAndCommunityId(getCurrentUser(), createCommunityMemberForm.getCommunityId()).orElse(null);
        if (communityMember == null) {
            throw new NotFoundException("[Community Member] You have not joined this community yet!", ErrorCode.COMMUNITY_MEMBER_ERROR_NOT_FOUND);
        }
        communityMemberRepository.delete(communityMember);
        apiMessageDto.setData(communityMember.getId());
        apiMessageDto.setMessage("You have left the " + communityMember.getCommunity().getName() + " community.");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CommunityMemberDto> getMember(@PathVariable Long id) {
        ApiMessageDto<CommunityMemberDto> apiMessageDto = new ApiMessageDto<>();
        CommunityMember communityMember = communityMemberRepository.findById(id).orElse(null);
        if (communityMember == null) {
            throw new NotFoundException("[Community Member] Community member not found!", ErrorCode.COMMUNITY_MEMBER_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(communityMemberMapper.fromEntityToDto(communityMember));
        apiMessageDto.setMessage("Get a community member success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<CommunityMemberDto>> listCommunityMember(CommunityMemberCriteria communityMemberCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CommunityMemberDto>> apiMessageDto = new ApiMessageDto<>();
        Page<CommunityMember> communityMemberPage = communityMemberRepository.findAll(communityMemberCriteria.getSpecification(), pageable);
        ResponseListDto<CommunityMemberDto> responseListObj = new ResponseListDto(communityMemberMapper.fromEntityToDtoList(communityMemberPage.getContent()), communityMemberPage.getTotalElements(), communityMemberPage.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List community member success.");
        return apiMessageDto;
    }
}
