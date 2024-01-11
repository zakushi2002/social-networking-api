package com.social.networking.api.mapper;

import com.social.networking.api.model.ExpertProfile;
import com.social.networking.api.dto.profile.expert.ExpertProfileDto;
import com.social.networking.api.form.profile.expert.CreateExpertAccountForm;
import com.social.networking.api.form.profile.expert.UpdateExpertAccountForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class, CategoryMapper.class})
public interface ExpertProfileMapper {
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @BeanMapping(ignoreByDefault = true)
    ExpertProfile fromCreateExpertAccountFormToEntity(CreateExpertAccountForm createExpertAccountForm);

    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.kind", target = "expertKind")
    @Mapping(source = "account.email", target = "expertEmail")
    @Mapping(source = "account.fullName", target = "expertFullName")
    @Mapping(source = "account.avatarPath", target = "expertAvatar")
    @Mapping(source = "account.group", target = "expertGroup", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "dob", target = "dateOfBirth")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "hospital", target = "hospital", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "hospitalRole", target = "hospitalRole", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "academicDegree", target = "academicDegree", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "department", target = "department", qualifiedByName = "fromEntityToShortDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoForClient")
    ExpertProfileDto fromEntityToDtoForClient(ExpertProfile expertProfile);

    @IterableMapping(elementTargetType = ExpertProfileDto.class, qualifiedByName = "fromEntityToDtoForClient")
    @Named("fromEntityListToDtoListForClient")
    List<ExpertProfileDto> fromEntityListToDtoListForClient(List<ExpertProfile> expertProfileList);

    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.kind", target = "expertKind")
    @Mapping(source = "account.email", target = "expertEmail")
    @Mapping(source = "account.fullName", target = "expertFullName")
    @Mapping(source = "account.avatarPath", target = "expertAvatar")
    @Mapping(source = "account.group", target = "expertGroup", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "dob", target = "dateOfBirth")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "hospital", target = "hospital", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "hospitalRole", target = "hospitalRole", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "academicDegree", target = "academicDegree", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "department", target = "department", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoForServer")
    ExpertProfileDto fromEntityToDtoForServer(ExpertProfile expertProfile);

    @IterableMapping(elementTargetType = ExpertProfileDto.class, qualifiedByName = "fromEntityToDtoForServer")
    @Named("fromEntityListToDtoListForServer")
    List<ExpertProfileDto> fromEntityListToDtoListForServer(List<ExpertProfile> expertProfileList);

    @Mapping(source = "fullName", target = "account.fullName")
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateExpertAccountFormToEntity(UpdateExpertAccountForm updateExpertAccountForm, @MappingTarget ExpertProfile expertProfile);
}
