package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.*;
import com.social.networking.api.model.criteria.BookmarkCriteria;
import com.social.networking.api.model.criteria.PostCriteria;
import com.social.networking.api.model.criteria.PostReactionCriteria;
import com.social.networking.api.repository.*;
import com.social.networking.api.view.dto.ApiMessageDto;
import com.social.networking.api.view.dto.ErrorCode;
import com.social.networking.api.view.dto.ResponseListDto;
import com.social.networking.api.view.dto.post.PostDto;
import com.social.networking.api.view.dto.post.bookmark.BookmarkDto;
import com.social.networking.api.view.dto.reaction.PostReactionDto;
import com.social.networking.api.view.form.post.HandlePostForm;
import com.social.networking.api.view.form.post.CreatePostForm;
import com.social.networking.api.view.form.post.UpdatePostForm;
import com.social.networking.api.view.form.post.bookmark.CreateBookmarkForm;
import com.social.networking.api.view.form.reaction.post.ReactPostForm;
import com.social.networking.api.view.mapper.BookmarkMapper;
import com.social.networking.api.view.mapper.PostMapper;
import com.social.networking.api.view.mapper.ReactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PostController extends BaseController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostMapper postMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ReactionMapper reactionMapper;
    @Autowired
    PostReactionRepository postReactionRepository;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    BookmarkMapper bookmarkMapper;
    @Autowired
    RelationshipRepository relationshipRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('POST_C')")
    @Transactional
    public ApiMessageDto<Long> createPost(@Valid @RequestBody CreatePostForm createPostForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account is not exist");
            return apiMessageDto;
        }
        if (createPostForm.getKind().equals(SocialNetworkingConstant.POST_KIND_NORMAL) && StringUtils.isEmpty(createPostForm.getTitle().trim()))
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_TITLE_REQUIRED);
            apiMessageDto.setMessage("Title is required");
            return apiMessageDto;
        }
        Post post = postMapper.fromCreatePostFormToEntity(createPostForm);
        post.setAccount(account);
        postRepository.save(post);
        apiMessageDto.setMessage("Create post successfully");
        apiMessageDto.setData(post.getId());
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('POST_U')")
    @Transactional
    public ApiMessageDto<Long> updatePost(@Valid @RequestBody UpdatePostForm updatePostForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(updatePostForm.getId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Post is not exist");
            return apiMessageDto;
        }
        if (post.getKind().equals(SocialNetworkingConstant.POST_KIND_NORMAL) && StringUtils.isEmpty(updatePostForm.getTitle().trim()))
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_TITLE_REQUIRED);
            apiMessageDto.setMessage("Title is required");
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account is not exist");
            return apiMessageDto;
        }
        if (!post.getAccount().getId().equals(account.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_OWNER);
            apiMessageDto.setMessage("You are not owner of this post");
            return apiMessageDto;
        }
        postMapper.mappingUpdatePostFormToEntity(updatePostForm, post);
        postRepository.save(post);
        apiMessageDto.setMessage("Update post successfully");
        apiMessageDto.setData(post.getId());
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('POST_D')")
    @Transactional
    public ApiMessageDto<Long> deletePost(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Post is not exist");
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account is not exist");
            return apiMessageDto;
        }
        if (!post.getAccount().getId().equals(account.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_OWNER);
            apiMessageDto.setMessage("You are not owner of this post");
            return apiMessageDto;
        }
        postRepository.deleteById(id);
        apiMessageDto.setMessage("Delete post successfully");
        apiMessageDto.setData(id);
        return apiMessageDto;
    }

    @GetMapping(value = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('POST_V')")
    public ApiMessageDto<PostDto> getPostDetail(@PathVariable("id") Long id) {
        ApiMessageDto<PostDto> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Post is not exist");
            return apiMessageDto;
        }
        apiMessageDto.setData(postMapper.fromEntityToPostDto(post));
        apiMessageDto.setMessage("Get post detail successfully");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('POST_L')")
    public ApiMessageDto<ResponseListDto<PostDto>> listPost(PostCriteria postCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<PostDto>> apiMessageDto = new ApiMessageDto<>();
        HashMap<Long, String> map = new HashMap<>();
        if (postCriteria.getFollowing() != null && postCriteria.getFollowing()) {
            List<Relationship> followingList = relationshipRepository.findAllByFollowerId(getCurrentUser());
            if (followingList != null && !followingList.isEmpty()) {
                for (Relationship relationship : followingList) {
                    map.put(relationship.getAccount().getId(), "");
                }
            }
            postCriteria.setFollowerId(getCurrentUser());
        }
        Page<Post> page = postRepository.findAll(postCriteria.getSpecification(map), pageable);
        ResponseListDto<PostDto> responseListDto = new ResponseListDto(postMapper.fromEntityToPostDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list post success");
        return apiMessageDto;
    }

    @PostMapping(value = "/react", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REACT_POST')")
    @Transactional
    public ApiMessageDto<PostReactionDto> react(@Valid @RequestBody ReactPostForm reactPostForm, BindingResult bindingResult) {
        ApiMessageDto<PostReactionDto> apiMessageDto = new ApiMessageDto<>();
        PostReaction postReaction = postReactionRepository.findByPostIdAndAccountIdAndKind(reactPostForm.getPostId(), getCurrentUser(), reactPostForm.getKind()).orElse(null);
        if (postReaction != null) {
            if (!postReaction.getAccount().getId().equals(getCurrentUser())) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.POST_REACTION_ERROR_NOT_OWNER);
                apiMessageDto.setMessage("Post reaction is not owner");
                return apiMessageDto;
            }
            postReactionRepository.deleteById(postReaction.getId());
            apiMessageDto.setMessage("Un-react post successfully");
            apiMessageDto.setData(reactionMapper.fromEntityToPostReactionDto(postReaction));
            return apiMessageDto;
        }
        Post post = postRepository.findById(reactPostForm.getPostId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Post is not exist");
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account is not exist");
            return apiMessageDto;
        }
        postReaction = reactionMapper.fromCreatePostReactionFormToEntity(reactPostForm);
        postReaction.setPost(post);
        postReaction.setAccount(account);
        postReactionRepository.save(postReaction);
        post.getPostReactions().add(postReaction);
        postRepository.save(post);
        apiMessageDto.setMessage("React post successfully");
        apiMessageDto.setData(reactionMapper.fromEntityToPostReactionDto(postReaction));
        return apiMessageDto;
    }

    @GetMapping(value = "/list-reaction", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REACT_POST_L')")
    public ApiMessageDto<ResponseListDto<PostReactionDto>> listPostReaction(@Valid PostReactionCriteria postReactionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<PostReactionDto>> apiMessageDto = new ApiMessageDto<>();
        Page<PostReaction> page = postReactionRepository.findAll(postReactionCriteria.getSpecification(), pageable);
        ResponseListDto<PostReactionDto> responseListDto = new ResponseListDto(reactionMapper.fromEntitiesToPostReactionDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list post reaction success");
        return apiMessageDto;
    }

    @PostMapping(value = "/bookmark", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOOKMARK_C')")
    @Transactional
    public ApiMessageDto<Long> bookmark(@Valid @RequestBody CreateBookmarkForm createBookmarkForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(createBookmarkForm.getPostId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Post is not exist");
            return apiMessageDto;
        }
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Account is not exist");
            return apiMessageDto;
        }
        Bookmark bookmark = bookmarkRepository.findFirstByAccountIdAndPostId(getCurrentUser(), createBookmarkForm.getPostId()).orElse(null);
        if (bookmark != null) {
            if (!bookmark.getAccount().getId().equals(getCurrentUser())) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.BOOKMARK_ERROR_EXIST);
                apiMessageDto.setMessage("Bookmark is not owner");
                return apiMessageDto;
            }
            bookmarkRepository.deleteById(bookmark.getId());
            apiMessageDto.setMessage("Remove bookmark successfully");
        } else {
            bookmark = new Bookmark();
            bookmark.setAccount(account);
            bookmark.setPost(post);
            bookmarkRepository.save(bookmark);
            apiMessageDto.setMessage("Add bookmark successfully");
        }
        apiMessageDto.setData(bookmark.getId());
        return apiMessageDto;
    }

    @GetMapping(value = "/list-bookmark", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('BOOKMARK_L')")
    public ApiMessageDto<ResponseListDto<BookmarkDto>> listBookmark(BookmarkCriteria bookmarkCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<BookmarkDto>> apiMessageDto = new ApiMessageDto<>();
        bookmarkCriteria.setAccountId(getCurrentUser());
        Page<Bookmark> page = bookmarkRepository.findAll(bookmarkCriteria.getSpecification(), pageable);
        ResponseListDto<BookmarkDto> responseListDto = new ResponseListDto(bookmarkMapper.fromEntitiesToBookmarkDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list bookmark success");
        return apiMessageDto;
    }

    @PutMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('POST_APPROVE')")
    @Transactional
    public ApiMessageDto<Long> approvePost(@Valid @RequestBody HandlePostForm handlePostForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(handlePostForm.getId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Post is not exist");
            return apiMessageDto;
        }
        if (!post.getStatus().equals(SocialNetworkingConstant.STATUS_PENDING)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_HANDLED);
            apiMessageDto.setMessage("Post has been handled!");
            return apiMessageDto;
        }
        post.setStatus(SocialNetworkingConstant.STATUS_ACTIVE);
        post.setModeratedDate(new Date());
        postRepository.save(post);
        apiMessageDto.setMessage("Approve post successfully");
        apiMessageDto.setData(post.getId());
        return apiMessageDto;
    }

    @PutMapping(value = "/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('POST_REJECT')")
    @Transactional
    public ApiMessageDto<Long> rejectPost(@Valid @RequestBody HandlePostForm handlePostForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(handlePostForm.getId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Post is not exist");
            return apiMessageDto;
        }
        if (!post.getStatus().equals(SocialNetworkingConstant.STATUS_PENDING)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_HANDLED);
            apiMessageDto.setMessage("Post has been handled!");
            return apiMessageDto;
        }
        postRepository.deleteById(handlePostForm.getId());
        return apiMessageDto;
    }
}
