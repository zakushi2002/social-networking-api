package com.social.networking.api.mapper;

import com.social.networking.api.model.Post;
import com.social.networking.api.dto.post.PostDto;
import com.social.networking.api.form.post.CreatePostForm;
import com.social.networking.api.form.post.UpdatePostForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, ReactionMapper.class, CommentMapper.class})
public interface PostMapper {
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "privacy", target = "privacy")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    Post fromCreatePostFormToEntity(CreatePostForm createPostForm);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "privacy", target = "privacy")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdatePostFormToEntity(UpdatePostForm updatePostForm, @MappingTarget Post post);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "account", target = "owner", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "privacy", target = "privacy")
    @Mapping(source = "postReactions", target = "postReactions", qualifiedByName = "fromEntitiesToPostReactionDtoShortList")
    @Mapping(source = "commentList", target = "commentList", qualifiedByName = "fromEntityToCountListComment")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "moderatedDate", target = "moderatedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPostDto")
    PostDto fromEntityToPostDto(Post post);

    @IterableMapping(elementTargetType = PostDto.class, qualifiedByName = "fromEntityToPostDto")
    @Named("fromEntityToPostDtoList")
    List<PostDto> fromEntityToPostDtoList(List<Post> posts);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "account", target = "owner", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "privacy", target = "privacy")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPostDtoForAdmin")
    PostDto fromEntityToPostDtoForAdmin(Post post);

    @IterableMapping(elementTargetType = PostDto.class, qualifiedByName = "fromEntityToPostDtoForAdmin")
    @Named("fromEntityToPostDtoListForAdmin")
    List<PostDto> fromEntityToPostDtoListForAdmin(List<Post> posts);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAutoCompleteDto")
    PostDto fromEntityToAutoCompleteDto(Post post);

    @Mapping(source = "id", target = "id")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCreateCommentNotificationDto")
    PostDto fromEntityToCreateCommentNotificationDto(Post post);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "account", target = "owner", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "postReactions", target = "postReactions", qualifiedByName = "fromEntitiesToPostReactionDtoShortList")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "moderatedDate", target = "moderatedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPostDtoForBookmark")
    PostDto fromEntityToPostDtoForBookmark(Post post);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "account", target = "owner", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPostDtoForBookmarkAdmin")
    PostDto fromEntityToPostDtoForBookmarkAdmin(Post post);
}
