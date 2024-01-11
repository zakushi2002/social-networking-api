package com.social.networking.api.controller;

import com.social.networking.api.model.Account;
import com.social.networking.api.model.Conversation;
import com.social.networking.api.model.ConversationAccount;
import com.social.networking.api.model.Message;
import com.social.networking.api.model.criteria.MessageCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.ConversationAccountRepository;
import com.social.networking.api.repository.ConversationRepository;
import com.social.networking.api.repository.MessageRepository;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.message.MessageDto;
import com.social.networking.api.view.form.message.SendMessageForm;
import com.social.networking.api.view.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/message")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class MessageController extends BaseController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    ConversationAccountRepository conversationAccountRepository;

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MESSAGE_SEND')")
    public ApiMessageDto<MessageDto> sendMessage(@Valid @RequestBody SendMessageForm sendMessageForm, BindingResult bindingResult) {
        ApiMessageDto<MessageDto> apiMessageDto = new ApiMessageDto<>();
        Conversation conversation = conversationRepository.findById(sendMessageForm.getConversationId()).orElse(null);
        if (conversation == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.CONVERSATION_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        ConversationAccount conversationAccount = conversationAccountRepository.findByConversationIdAndAccountId(conversation.getId(), account.getId()).orElse(null);
        if (conversationAccount == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.CONVERSATION_ERROR_NOT_MEMBER);
            return apiMessageDto;
        }
        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(account);
        message.setContent(sendMessageForm.getContent());
        messageRepository.save(message);
        conversation.setLastMessage(message.getContent());
        conversationRepository.save(conversation);
        apiMessageDto.setData(messageMapper.fromEntityToDto(message));
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MESSAGE_V')")
    public ApiMessageDto<MessageDto> getMessage(@PathVariable("id") Long id) {
        ApiMessageDto<MessageDto> apiMessageDto = new ApiMessageDto<>();
        Message message = messageRepository.findById(id).orElse(null);
        if (message == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.MESSAGE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setData(messageMapper.fromEntityToDto(message));
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MESSAGE_L')")
    public ApiMessageDto<ResponseListDto<MessageDto>> listMessage(@Valid MessageCriteria messageCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<MessageDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Message> messagePage = messageRepository.findAll(messageCriteria.getSpecification(), pageable);
        ResponseListDto<MessageDto> responseListDto = new ResponseListDto(messageMapper.fromEntityToDtoList(messagePage.getContent()), messagePage.getTotalElements(), messagePage.getTotalPages());
        apiMessageDto.setMessage("List message successfully!");
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MESSAGE_D')")
    public ApiMessageDto<Long> deleteMessage(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Message message = messageRepository.findById(id).orElse(null);
        if (message == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.MESSAGE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!message.getSender().getId().equals(getCurrentUser())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.MESSAGE_ERROR_NOT_OWNER);
            return apiMessageDto;
        }
        Conversation conversation = message.getConversation();
        Message checkLastMessage = messageRepository.findFirstByConversationIdAndContentOrderByCreatedDateDesc(conversation.getId(), conversation.getLastMessage()).orElse(null);
        if (checkLastMessage != null && checkLastMessage.getId().equals(id)) {
            conversation.setLastMessage(null);
        }
        messageRepository.deleteById(id);
        conversationRepository.save(conversation);
        apiMessageDto.setMessage("Delete message successfully!");
        apiMessageDto.setData(id);
        return apiMessageDto;
    }
}
