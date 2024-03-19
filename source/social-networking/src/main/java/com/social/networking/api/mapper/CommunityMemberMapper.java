package com.social.networking.api.mapper;

import com.social.networking.api.model.CommunityMember;
import com.social.networking.api.dto.community.member.CommunityMemberDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, CategoryMapper.class})
public interface CommunityMemberMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "community", target = "community", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "createdDate", target = "createdDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    CommunityMemberDto fromEntityToDto(CommunityMember communityMember);

    @IterableMapping(elementTargetType = CommunityMemberDto.class, qualifiedByName = "fromEntityToDto")
    @Named("fromEntityToDtoList")
    List<CommunityMemberDto> fromEntityToDtoList(List<CommunityMember> communityMembers);
}
