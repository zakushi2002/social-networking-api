package com.social.networking.api.view.mapper;

import com.social.networking.api.model.UserProfile;
import com.social.networking.api.view.dto.profile.user.UserProfileDto;
import com.social.networking.api.view.form.profile.user.CreateUserAccountForm;
import com.social.networking.api.view.form.profile.user.UpdateUserAccountForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class})
public interface UserProfileMapper {
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @BeanMapping(ignoreByDefault = true)
    UserProfile fromCreateUserAccountFormToEntity(CreateUserAccountForm createUserAccountForm);

    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.kind", target = "userKind")
    @Mapping(source = "account.email", target = "userEmail")
    @Mapping(source = "account.fullName", target = "userFullName")
    @Mapping(source = "account.avatarPath", target = "userAvatar")
    @Mapping(source = "dob", target = "dateOfBirth")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoForClient")
    UserProfileDto fromEntityToDtoForClient(UserProfile userProfile);

    @IterableMapping(elementTargetType = UserProfileDto.class, qualifiedByName = "fromEntityToDtoForClient")
    @Named("fromEntityListToDtoListForClient")
    List<UserProfileDto> fromEntityListToDtoListForClient(List<UserProfile> userProfileList);

    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.kind", target = "userKind")
    @Mapping(source = "account.email", target = "userEmail")
    @Mapping(source = "account.fullName", target = "userFullName")
    @Mapping(source = "account.avatarPath", target = "userAvatar")
    @Mapping(source = "account.group", target = "userGroup", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "dob", target = "dateOfBirth")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoForServer")
    UserProfileDto fromEntityToDtoForServer(UserProfile userProfile);

    @IterableMapping(elementTargetType = UserProfileDto.class, qualifiedByName = "fromEntityToDtoForServer")
    @Named("fromEntityListToDtoListForServer")
    List<UserProfileDto> fromEntityListToDtoListForServer(List<UserProfile> userProfileList);

    @Mapping(source = "fullName", target = "account.fullName")
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateUserAccountFormToEntity(UpdateUserAccountForm updateUserAccountForm, @MappingTarget UserProfile userProfile);
}
