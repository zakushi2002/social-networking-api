package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.BadRequestException;
import com.social.networking.api.exception.NotFoundException;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Conversation;
import com.social.networking.api.model.ConversationAccount;
import com.social.networking.api.model.criteria.ConversationAccountCriteria;
import com.social.networking.api.model.criteria.ConversationCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.ConversationAccountRepository;
import com.social.networking.api.repository.ConversationRepository;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.conversation.ConversationDto;
import com.social.networking.api.dto.conversation.account.ConversationAccountDto;
import com.social.networking.api.form.conversation.ChangeLastMessageForm;
import com.social.networking.api.form.conversation.CreateConversationForm;
import com.social.networking.api.form.conversation.UpdateConversationForm;
import com.social.networking.api.mapper.ConversationAccountMapper;
import com.social.networking.api.mapper.ConversationMapper;
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
@RequestMapping("/v1/conversation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ConversationController extends BaseController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    ConversationMapper conversationMapper;
    @Autowired
    ConversationAccountRepository conversationAccountRepository;
    @Autowired
    ConversationAccountMapper conversationAccountMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_L')")
    public ApiMessageDto<ResponseListDto<ConversationDto>> listConversation(ConversationCriteria conversationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ConversationDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Conversation> conversationPage = conversationRepository.findAll(conversationCriteria.getSpecification(null), pageable);
        ResponseListDto<ConversationDto> responseListDto = new ResponseListDto(conversationMapper.fromEntityToConversationDtoShortList(conversationPage.getContent()), conversationPage.getTotalElements(), conversationPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List conversation success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_V')")
    public ApiMessageDto<ConversationDto> getConversation(@PathVariable("id") Long id) {
        ApiMessageDto<ConversationDto> apiMessageDto = new ApiMessageDto<>();
        Conversation conversation = conversationRepository.findById(id).orElse(null);
        if (conversation == null) {
            throw new NotFoundException("[Conversation] Not found conversation!", ErrorCode.CONVERSATION_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(conversationMapper.fromEntityToConversationDto(conversation));
        apiMessageDto.setMessage("Get conversation success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-account/{conversationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_L')")
    public ApiMessageDto<ConversationDto> listAccountInConversation(@PathVariable("conversationId") Long conversationId) {
        ApiMessageDto<ConversationDto> apiMessageDto = new ApiMessageDto<>();
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
        if (conversation == null) {
            throw new NotFoundException("[Conversation] Not found conversation!", ErrorCode.CONVERSATION_ERROR_NOT_FOUND);
        }
        apiMessageDto.setData(conversationMapper.fromEntityToConversationDtoComplete(conversation));
        apiMessageDto.setMessage("List account in conversation success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_C')")
    @Transactional
    public ApiMessageDto<ConversationDto> createConversation(@Valid @RequestBody CreateConversationForm createConversationForm, BindingResult bindingResult) {
        ApiMessageDto<ConversationDto> apiMessageDto = new ApiMessageDto<>();
        List<ConversationAccount> conversationAccountList = new ArrayList<>();
        if (createConversationForm.getAccountIds().length == 0) {
            throw new BadRequestException("[Conversation] Account ids is empty!", ErrorCode.CONVERSATION_ERROR_ACCOUNT_ID_EMPTY);
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            throw new NotFoundException("[Conversation] Not found account!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
        }
        Conversation conversation = conversationMapper.fromCreateConversationFormToEntity(createConversationForm);
        conversationRepository.save(conversation);
        // Add friend to conversation
        if (createConversationForm.getAccountIds().length == 1 && createConversationForm.getKind().equals(SocialNetworkingConstant.CONVERSATION_KIND_PRIVATE)) {
            ConversationAccount conversationAccount = new ConversationAccount();
            Account friend = accountRepository.findById(createConversationForm.getAccountIds()[0]).orElse(null);
            if (friend != null) {
                conversationAccount.setAccount(friend);
                conversationAccount.setConversation(conversation);
                conversationAccountList.add(conversationAccount);
            }
        } else if (createConversationForm.getAccountIds().length > 1 && createConversationForm.getKind().equals(SocialNetworkingConstant.CONVERSATION_KIND_GROUP)) {
            for (Long accountId : createConversationForm.getAccountIds()) {
                ConversationAccount conversationAccount = new ConversationAccount();
                Account friend = accountRepository.findById(accountId).orElse(null);
                if (friend != null) {
                    conversationAccount.setAccount(friend);
                    conversationAccount.setConversation(conversation);
                    conversationAccountList.add(conversationAccount);
                }
            }
        }

        if (conversationAccountList.isEmpty()) {
            throw new BadRequestException("[Conversation] Account id not found!", ErrorCode.CONVERSATION_ERROR_ACCOUNT_ID_NOT_FOUND);
        }
        // Add current user to conversation
        ConversationAccount conversationAccount = new ConversationAccount();
        conversationAccount.setAccount(account);
        conversationAccount.setConversation(conversation);
        conversationAccountList.add(conversationAccount);
        conversationAccountRepository.saveAll(conversationAccountList);
        conversation.setAccountList(conversationAccountList);

        apiMessageDto.setData(conversationMapper.fromEntityToConversationDtoComplete(conversation));
        apiMessageDto.setMessage("Create conversation success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-conversation-account", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_USER_L')")
    public ApiMessageDto<ResponseListDto<ConversationAccountDto>> listConversationAccount(ConversationAccountCriteria conversationAccountCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ConversationAccountDto>> apiMessageDto = new ApiMessageDto<>();
        Page<ConversationAccount> conversationAccountPage = conversationAccountRepository.findAll(conversationAccountCriteria.getSpecification(), pageable);
        ResponseListDto<ConversationAccountDto> responseListDto;
        responseListDto = new ResponseListDto(conversationAccountMapper.fromConversationAccountToDtoForListConversationResponseListDto(conversationAccountPage.getContent()), conversationAccountPage.getTotalElements(), conversationAccountPage.getTotalPages());
        apiMessageDto.setMessage("List conversation account success");
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_U')")
    @Transactional
    public ApiMessageDto<ConversationDto> updateConversation(@Valid @RequestBody UpdateConversationForm updateConversationForm, BindingResult bindingResult) {
        ApiMessageDto<ConversationDto> apiMessageDto = new ApiMessageDto<>();
        Conversation conversation = conversationRepository.findById(updateConversationForm.getId()).orElse(null);
        if (conversation == null) {
            throw new NotFoundException("[Conversation] Not found conversation!", ErrorCode.CONVERSATION_ERROR_NOT_FOUND);
        }
        conversationMapper.mappingUpdateConversationFormToEntity(updateConversationForm, conversation);
        conversationRepository.save(conversation);
        apiMessageDto.setData(conversationMapper.fromEntityToConversationDtoShort(conversation));
        apiMessageDto.setMessage("Update conversation success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_D')")
    @Transactional
    public ApiMessageDto<Long> deleteConversation(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Conversation conversation = conversationRepository.findById(id).orElse(null);
        if (conversation == null) {
            throw new NotFoundException("[Conversation] Not found conversation!", ErrorCode.CONVERSATION_ERROR_NOT_FOUND);
        }
        conversationRepository.deleteById(id);
        apiMessageDto.setData(id);
        apiMessageDto.setMessage("Delete conversation success");
        return apiMessageDto;
    }

    @PutMapping(value = "/change-last-message", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_U')")
    @Transactional
    public ApiMessageDto<ConversationDto> changeLastMessage(@Valid @RequestBody ChangeLastMessageForm changeLastMessageForm, BindingResult bindingResult) {
        ApiMessageDto<ConversationDto> apiMessageDto = new ApiMessageDto<>();
        Conversation conversation = conversationRepository.findById(changeLastMessageForm.getId()).orElse(null);
        if (conversation == null) {
            throw new NotFoundException("[Conversation] Not found conversation!", ErrorCode.CONVERSATION_ERROR_NOT_FOUND);
        }
        conversation.setLastMessage(changeLastMessageForm.getLastMessage());
        conversationRepository.save(conversation);
        apiMessageDto.setData(conversationMapper.fromEntityToConversationDtoShort(conversation));
        apiMessageDto.setMessage("Change last message success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-conversation", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CONVERSATION_USER_L')")
    public ApiMessageDto<ResponseListDto<ConversationDto>> listConversationForUser(ConversationCriteria conversationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ConversationDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Conversation> conversationPage = conversationRepository.findAll(conversationCriteria.getSpecification(getCurrentUser()), pageable);
        ResponseListDto<ConversationDto> responseListDto = new ResponseListDto(conversationMapper.fromEntityToConversationDtoCompleteList(conversationPage.getContent()), conversationPage.getTotalElements(), conversationPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List conversation success.");
        return apiMessageDto;
    }
}
