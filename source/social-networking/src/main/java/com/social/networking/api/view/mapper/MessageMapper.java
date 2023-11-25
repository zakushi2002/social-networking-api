package com.social.networking.api.view.mapper;

import com.social.networking.api.model.Message;
import com.social.networking.api.view.dto.message.MessageDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "conversation.id", target = "conversationId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    MessageDto fromEntityToDto(Message message);

    @IterableMapping(elementTargetType = MessageDto.class, qualifiedByName = "fromEntityToDto")
    @Named("fromEntityToDtoList")
    List<MessageDto> fromEntityToDtoList(List<Message> messages);
}
