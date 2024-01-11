package com.social.networking.api.mapper;

import com.social.networking.api.model.Comment;
import com.social.networking.api.dto.comment.CommentDto;
import com.social.networking.api.form.comment.CreateCommentForm;
import com.social.networking.api.form.comment.UpdateCommentForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, PostMapper.class, ReactionMapper.class})
public interface CommentMapper {
    @Mapping(source = "commentContent", target = "content")
    @Mapping(source = "postId", target = "post.id")
    @BeanMapping(ignoreByDefault = true)
    Comment fromCreateCommentFormToEntity(CreateCommentForm createCommentForm);

    @Mapping(source = "commentContent", target = "content")
    @BeanMapping(ignoreByDefault = true)
    void mappingForUpdateComment(UpdateCommentForm updateCommentForm, @MappingTarget Comment comment);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "post", target = "post", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Mapping(source = "content", target = "commentContent")
    @Mapping(source = "depth", target = "commentDepth")
    @Mapping(source = "commentReactions", target = "commentReactions", qualifiedByName = "fromEntitiesToCommentReactionDtoList")
    @Mapping(source = "account", target = "owner", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCommentDto")
    CommentDto fromEntityToCommentDto(Comment comment);

    @IterableMapping(elementTargetType = CommentDto.class, qualifiedByName = "fromEntityToCommentDto")
    List<CommentDto> fromEntitiesToCommentDtoList(List<Comment> comments);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "post", target = "post", qualifiedByName = "fromEntityToCreateCommentNotificationDto")
    @Mapping(source = "content", target = "commentContent")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCreateCommentDto")
    CommentDto fromEntityToCreateCommentDto(Comment comment);

    @Mapping(source = "id", target = "id")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCountComment")
    CommentDto fromEntityToCountComment(Comment comment);

    @IterableMapping(elementTargetType = CommentDto.class, qualifiedByName = "fromEntityToCountComment")
    @Named("fromEntityToCountListComment")
    List<CommentDto> fromEntityToCountListComment(List<Comment> comments);
}
