package com.social.networking.api.view.mapper;

import com.social.networking.api.model.Relationship;
import com.social.networking.api.dto.relationship.RelationshipDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class})
public interface RelationshipMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "follower", target = "follower", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToRelationshipDto")
    RelationshipDto fromEntityToRelationshipDto(Relationship relationship);

    @Mapping(source = "follower", target = "follower", qualifiedByName = "fromAccountToAutoCompleteDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("viewMyFollower")
    RelationshipDto viewMyFollower(Relationship relationship);

    @IterableMapping(elementTargetType = RelationshipDto.class, qualifiedByName = "viewMyFollower")
    @Named("viewMyFollowerList")
    List<RelationshipDto> viewMyFollowerList(List<Relationship> relationships);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "account", target = "account", qualifiedByName = "fromAccountToAutoCompleteDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("viewMyFollowing")
    RelationshipDto viewMyFollowing(Relationship relationship);

    @IterableMapping(elementTargetType = RelationshipDto.class, qualifiedByName = "viewMyFollowing")
    @Named("viewMyFollowingList")
    List<RelationshipDto> viewMyFollowingList(List<Relationship> relationships);
}
