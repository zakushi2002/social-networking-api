package com.social.networking.api.mapper;

import com.social.networking.api.model.ConversationAccount;
import com.social.networking.api.dto.conversation.account.ConversationAccountDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, ConversationMapper.class})
public interface ConversationAccountMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "conversation", target = "conversation", qualifiedByName = "fromEntityToConversationDtoShort")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromEntityToShortDtoForNotification")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromConversationAccountToDtoForListConversation")
    ConversationAccountDto fromConversationAccountToDtoForListConversation(ConversationAccount conversationAccount);

    @IterableMapping(elementTargetType = ConversationAccountDto.class, qualifiedByName = "fromConversationAccountToDtoForListConversation")
    @Named("fromConversationAccountToDtoForListConversationResponseListDto")
    List<ConversationAccountDto> fromConversationAccountToDtoForListConversationResponseListDto(List<ConversationAccount> conversationAccounts);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "conversation", target = "conversation", qualifiedByName = "fromEntityToConversationDtoForListAccount")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromAccountToAutoCompleteDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromConversationAccountToDtoForListAccount")
    ConversationAccountDto fromConversationAccountToDtoForListAccount(ConversationAccount conversationAccount);

    @IterableMapping(elementTargetType = ConversationAccountDto.class, qualifiedByName = "fromConversationAccountToDtoForListAccount")
    @Named("fromConversationAccountToDtoForListAccountResponseListDto")
    List<ConversationAccountDto> fromConversationAccountToDtoForListAccountResponseListDto(List<ConversationAccount> conversationAccounts);

    // @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromAccountToAutoCompleteDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromConversationAccountToDtoForListAccountShort")
    ConversationAccountDto fromConversationAccountToDtoForListAccountShort(ConversationAccount conversationAccount);
}
