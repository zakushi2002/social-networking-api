package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.message.comment.CommentMessage;
import com.social.networking.api.message.reaction.CommentReactionMessage;
import com.social.networking.api.model.*;
import com.social.networking.api.model.criteria.CommentCriteria;
import com.social.networking.api.model.criteria.CommentReactionCriteria;
import com.social.networking.api.repository.*;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.comment.CommentDto;
import com.social.networking.api.dto.reaction.CommentReactionDto;
import com.social.networking.api.form.comment.CreateCommentForm;
import com.social.networking.api.form.comment.UpdateCommentForm;
import com.social.networking.api.form.reaction.comment.ReactCommentForm;
import com.social.networking.api.mapper.CommentMapper;
import com.social.networking.api.mapper.ReactionMapper;
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
import java.util.Objects;

@RestController
@RequestMapping("/v1/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CommentController extends BaseController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ReactionMapper reactionMapper;
    @Autowired
    CommentReactionRepository commentReactionRepository;
    @Autowired
    CommentReactionMessage commentReactionMessage;
    @Autowired
    CommentMessage commentMessage;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CMT_C')")
    @Transactional
    public ApiMessageDto<CommentDto> createComment(@Valid @RequestBody CreateCommentForm createCommentForm, BindingResult bindingResult) {
        ApiMessageDto<CommentDto> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(createCommentForm.getPostId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode("POST_ERROR_NOT_FOUND");
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode("ACCOUNT_ERROR_NOT_FOUND");
            return apiMessageDto;
        }
        Comment comment = commentMapper.fromCreateCommentFormToEntity(createCommentForm);
        if (createCommentForm.getParentId() != null) {
            Comment parent = commentRepository.findById(createCommentForm.getParentId()).orElse(null);
            if (parent == null) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            if (parent.getDepth().equals(SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_2)) {
                comment.setParent(parent.getParent());
            } else {
                comment.setParent(parent);
            }
            comment.setDepth(SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_2);
        } else {
            comment.setDepth(SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_1);
        }
        List<Long> taggedAccountIds = new ArrayList<>();
        if (createCommentForm.getTaggedAccountIds() != null) {
            for (Long taggedAccountId : createCommentForm.getTaggedAccountIds()) {
                Account taggedAccount = accountRepository.findById(taggedAccountId).orElse(null);
                if (taggedAccount != null) {
                    taggedAccountIds.add(taggedAccountId);
                }
            }
        }
        boolean tagOwnerOfPost = taggedAccountIds.contains(post.getAccount().getId());
        comment.setAccount(account);
        commentRepository.save(comment);
        CommentDto commentDto = commentMapper.fromEntityToCreateCommentDto(comment);
        commentDto.setOwnerIdOfPost(post.getAccount().getId());
        commentDto.setTaggedAccountIds(taggedAccountIds);
        if (taggedAccountIds.isEmpty()) { // No tagged account
            if (Objects.equals(comment.getDepth(), SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_1)) {
                commentMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, comment, SocialNetworkingConstant.NOTIFICATION_KIND_COMMENT_IN_MY_POST);
            } else {
                commentMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, comment, SocialNetworkingConstant.NOTIFICATION_KIND_REPLIED_MY_COMMENT);
            }
        } else if (tagOwnerOfPost) { // Tag owner of post and other account
            if (!taggedAccountIds.contains(comment.getParent().getAccount().getId()) && Objects.equals(comment.getDepth(), SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_2)) { // Not tag owner of parent comment
                commentMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, comment, SocialNetworkingConstant.NOTIFICATION_KIND_REPLIED_MY_COMMENT);
            }
            commentMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, comment, SocialNetworkingConstant.NOTIFICATION_KIND_TAGGED_IN_COMMENT);
        } else { // Only tag other account
            if (Objects.equals(comment.getDepth(), SocialNetworkingConstant.COMMENT_DEPTH_LEVEL_1)) {
                commentMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, comment, SocialNetworkingConstant.NOTIFICATION_KIND_COMMENT_IN_MY_POST);
                commentMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, comment, SocialNetworkingConstant.NOTIFICATION_KIND_TAGGED_IN_COMMENT);
            } else {
                if (!taggedAccountIds.contains(comment.getParent().getAccount().getId())) { // Not tag owner of parent comment
                    commentMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, comment, SocialNetworkingConstant.NOTIFICATION_KIND_REPLIED_MY_COMMENT);
                }
                commentMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, comment, SocialNetworkingConstant.NOTIFICATION_KIND_TAGGED_IN_COMMENT);
            }
        }
        apiMessageDto.setMessage("Create comment successfully");
        apiMessageDto.setData(commentDto);
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CMT_U')")
    @Transactional
    public ApiMessageDto<Long> updateComment(@Valid @RequestBody UpdateCommentForm updateCommentForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Comment comment = commentRepository.findById(updateCommentForm.getId()).orElse(null);
        if (comment == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!comment.getAccount().getId().equals(account.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_OWNER);
            return apiMessageDto;
        }
        commentMapper.mappingForUpdateComment(updateCommentForm, comment);
        commentRepository.save(comment);
        apiMessageDto.setMessage("Update comment successfully");
        apiMessageDto.setData(comment.getId());
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CMT_D')")
    @Transactional
    public ApiMessageDto<Long> deleteComment(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!comment.getAccount().getId().equals(account.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_OWNER);
            return apiMessageDto;
        }
        commentRepository.deleteById(id);
        apiMessageDto.setMessage("Delete comment successfully");
        apiMessageDto.setData(comment.getId());
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CMT_V')")
    public ApiMessageDto<CommentDto> getComment(@PathVariable("id") Long id) {
        ApiMessageDto<CommentDto> apiMessageDto = new ApiMessageDto<>();
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setData(commentMapper.fromEntityToCommentDto(comment));
        apiMessageDto.setMessage("Get comment successfully");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CMT_L')")
    public ApiMessageDto<ResponseListDto<CommentDto>> listComment(@Valid CommentCriteria commentCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CommentDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Comment> page = commentRepository.findAll(commentCriteria.getSpecification(), pageable);
        ResponseListDto<CommentDto> responseListDto = new ResponseListDto(commentMapper.fromEntitiesToCommentDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get comment list success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CMT_POST_L')")
    public ApiMessageDto<ResponseListDto<CommentDto>> listCommentByPostId(@PathVariable("postId") Long postId, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CommentDto>> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Page<Comment> page = commentRepository.findByPostId(postId, pageable);
        ResponseListDto<CommentDto> responseListDto = new ResponseListDto(commentMapper.fromEntitiesToCommentDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get comment list success");
        return apiMessageDto;
    }

    @PostMapping(value = "/react", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REACT_CMT')")
    @Transactional
    public ApiMessageDto<CommentReactionDto> reactComment(@Valid @RequestBody ReactCommentForm reactCommentForm, BindingResult bindingResult) {
        ApiMessageDto<CommentReactionDto> apiMessageDto = new ApiMessageDto<>();
        CommentReaction commentReaction = commentReactionRepository.findByCommentIdAndAccountIdAndKind(reactCommentForm.getCommentId(), getCurrentUser(), reactCommentForm.getKind()).orElse(null);
        if (commentReaction != null) {
            if (!commentReaction.getAccount().getId().equals(getCurrentUser())) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.COMMENT_REACTION_ERROR_NOT_OWNER);
                apiMessageDto.setMessage("Comment reaction is not owner");
                return apiMessageDto;
            }
            commentReactionRepository.deleteById(commentReaction.getId());
            apiMessageDto.setMessage("Un-react comment successfully");
            apiMessageDto.setData(reactionMapper.fromEntityToCommentReactionDto(commentReaction));
            return apiMessageDto;
        }
        Comment comment = commentRepository.findById(reactCommentForm.getCommentId()).orElse(null);
        if (comment == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        commentReaction = reactionMapper.fromCreateCommentReactionFormToEntity(reactCommentForm);
        commentReaction.setComment(comment);
        commentReaction.setAccount(account);
        commentReactionRepository.save(commentReaction);
        comment.getCommentReactions().add(commentReaction);
        commentRepository.save(comment);
        if (!isAdmin()) {
            commentReactionMessage.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, commentReaction, SocialNetworkingConstant.NOTIFICATION_KIND_REACTION_MY_COMMENT);
        }
        apiMessageDto.setMessage("React comment successfully");
        apiMessageDto.setData(reactionMapper.fromEntityToCommentReactionDto(commentReaction));
        return apiMessageDto;
    }


    @GetMapping(value = "/list-reaction", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REACT_CMT_L')")
    public ApiMessageDto<ResponseListDto<CommentReactionDto>> listCommentReaction(@Valid CommentReactionCriteria commentReactionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CommentReactionDto>> apiMessageDto = new ApiMessageDto<>();
        Page<CommentReaction> page = commentReactionRepository.findAll(commentReactionCriteria.getSpecification(), pageable);
        ResponseListDto<CommentReactionDto> responseListDto = new ResponseListDto(reactionMapper.fromEntitiesToCommentReactionDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list comment reaction success");
        return apiMessageDto;
    }
}
