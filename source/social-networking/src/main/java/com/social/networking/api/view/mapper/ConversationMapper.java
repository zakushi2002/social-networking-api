package com.social.networking.api.view.mapper;

import com.social.networking.api.model.Conversation;
import com.social.networking.api.view.dto.conversation.ConversationDto;
import com.social.networking.api.view.form.conversation.CreateConversationForm;
import com.social.networking.api.view.form.conversation.UpdateConversationForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ConversationAccountMapper.class})
public interface ConversationMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "lastMessage", target = "lastMessage")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToConversationDto")
    ConversationDto fromEntityToConversationDto(Conversation conversation);

    @IterableMapping(elementTargetType = ConversationDto.class, qualifiedByName = "fromEntityToConversationDto")
    @Named("fromEntityToConversationDtoList")
    List<ConversationDto> fromEntityToConversationDtoList(List<Conversation> conversations);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "lastMessage", target = "lastMessage")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToConversationDtoShort")
    ConversationDto fromEntityToConversationDtoShort(Conversation conversation);

    @IterableMapping(elementTargetType = ConversationDto.class, qualifiedByName = "fromEntityToConversationDtoShort")
    @Named("fromEntityToConversationDtoShortList")
    List<ConversationDto> fromEntityToConversationDtoShortList(List<Conversation> conversations);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "lastMessage", target = "lastMessage")
    @BeanMapping(ignoreByDefault = true)
    Conversation fromCreateConversationFormToEntity(CreateConversationForm createConversationForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToConversationDtoForListAccount")
    ConversationDto fromEntityToConversationDtoForListAccount(Conversation conversation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "lastMessage", target = "lastMessage")
    @Mapping(source = "accountList", target = "accountList", qualifiedByName = "fromConversationAccountToDtoForListAccountShort")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToConversationDtoComplete")
    ConversationDto fromEntityToConversationDtoComplete(Conversation conversation);

    @IterableMapping(elementTargetType = ConversationDto.class, qualifiedByName = "fromEntityToConversationDtoComplete")
    @Named("fromEntityToConversationDtoCompleteList")
    List<ConversationDto> fromEntityToConversationDtoCompleteList(List<Conversation> conversations);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "image", target = "image")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateConversationFormToEntity(UpdateConversationForm updateConversationForm, @MappingTarget Conversation conversation);
}
