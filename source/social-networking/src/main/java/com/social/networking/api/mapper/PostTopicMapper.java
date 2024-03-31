package com.social.networking.api.mapper;

import com.social.networking.api.model.PostTopic;
import org.mapstruct.*;
import com.social.networking.api.dto.category.topic.PostTopicDto;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class})
public interface PostTopicMapper {
    // @Mapping(source = "topic", target = "topic", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "topic.id", target = "id")
    @Mapping(source = "topic.name", target = "nameTopic")
    @Named("fromEntityToPostTopicDto")
    PostTopicDto fromEntityToPostTopicDto(PostTopic postTopic);

    @IterableMapping(elementTargetType = PostTopicDto.class, qualifiedByName = "fromEntityToPostTopicDto")
    @Named("fromEntityToPostTopicDtoList")
    List<PostTopicDto> fromEntityListToPostTopicDtoList(List<PostTopic> postTopics);
}
