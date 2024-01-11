package com.social.networking.api.mapper;

import com.social.networking.api.model.CommentReaction;
import com.social.networking.api.model.PostReaction;
import com.social.networking.api.dto.reaction.CommentReactionDto;
import com.social.networking.api.dto.reaction.PostReactionDto;
import com.social.networking.api.form.reaction.comment.ReactCommentForm;
import com.social.networking.api.form.reaction.post.ReactPostForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReactionMapper {
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    PostReaction fromCreatePostReactionFormToEntity(ReactPostForm reactPostForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "post.account.id", target = "ownerPostId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPostReactionDto")
    PostReactionDto fromEntityToPostReactionDto(PostReaction postReaction);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "account.id", target = "accountId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToPostReactionDtoShort")
    PostReactionDto fromEntityToPostReactionDtoShort(PostReaction postReaction);

    @IterableMapping(elementTargetType = PostReactionDto.class, qualifiedByName = "fromEntityToPostReactionDto")
    @Named("fromEntitiesToPostReactionDtoList")
    List<PostReactionDto> fromEntitiesToPostReactionDtoList(List<PostReaction> postReactions);

    @IterableMapping(elementTargetType = PostReactionDto.class, qualifiedByName = "fromEntityToPostReactionDtoShort")
    @Named("fromEntitiesToPostReactionDtoShortList")
    List<PostReactionDto> fromEntitiesToPostReactionDtoShortList(List<PostReaction> postReactions);

    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    CommentReaction fromCreateCommentReactionFormToEntity(ReactCommentForm reactCommentForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "comment.account.id", target = "ownerCommentId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCommentReactionDto")
    CommentReactionDto fromEntityToCommentReactionDto(CommentReaction commentReaction);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "account.id", target = "accountId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCommentReactionDtoShort")
    CommentReactionDto fromEntityToCommentReactionDtoShort(CommentReaction commentReaction);

    @IterableMapping(elementTargetType = CommentReactionDto.class, qualifiedByName = "fromEntityToCommentReactionDtoShort")
    @Named("fromEntitiesToCommentReactionDtoShortList")
    List<CommentReactionDto> fromEntitiesToCommentReactionDtoShortList(List<CommentReaction> commentReactions);

    @IterableMapping(elementTargetType = CommentReactionDto.class, qualifiedByName = "fromEntityToCommentReactionDto")
    @Named("fromEntitiesToCommentReactionDtoList")
    List<CommentReactionDto> fromEntitiesToCommentReactionDtoList(List<CommentReaction> commentReactions);
}
