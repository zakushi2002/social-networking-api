package com.social.networking.api.view.mapper;

import com.social.networking.api.model.Account;
import com.social.networking.api.model.ExpertProfile;
import com.social.networking.api.model.UserProfile;
import com.social.networking.api.view.dto.account.AccountDto;
import com.social.networking.api.view.dto.account.AccountProfileDto;
import com.social.networking.api.view.dto.profile.expert.ExpertProfileDto;
import com.social.networking.api.view.dto.profile.user.UserProfileDto;
import com.social.networking.api.view.form.account.UpdateAdminForm;
import com.social.networking.api.view.form.profile.expert.CreateExpertAccountForm;
import com.social.networking.api.view.form.profile.user.CreateUserAccountForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class, CategoryMapper.class})
public interface AccountMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToDto")
    AccountDto fromAccountToDto(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToDtoProfile")
    AccountDto fromAccountToDtoProfile(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToAutoCompleteDto")
    AccountDto fromAccountToAutoCompleteDto(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToAutoCompleteDtoWithGroup")
    AccountDto fromAccountToAutoCompleteDtoWithGroup(Account account);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromAccountToDto")
    List<AccountDto> fromEntityToAccountDtoList(List<Account> accounts);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromAccountToAutoCompleteDto")
    @Named("fromEntityToAccountAutoCompleteDtoList")
    List<AccountDto> fromEntityToAccountAutoCompleteDtoList(List<Account> accounts);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateAdminFormToAccount(UpdateAdminForm updateAdminForm, @MappingTarget Account account);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @BeanMapping(ignoreByDefault = true)
    Account fromCreateUserAccountFormToEntity(CreateUserAccountForm createUserAccountForm);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @BeanMapping(ignoreByDefault = true)
    Account fromCreateExpertAccountFormToEntity(CreateExpertAccountForm createExpertAccountForm);

    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.kind", target = "kind")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.fullName", target = "fullName")
    @Mapping(source = "account.avatarPath", target = "avatar")
    @Mapping(source = "account.group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "dob", target = "dateOfBirth")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "hospital", target = "hospital", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "hospitalRole", target = "hospitalRole", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "academicDegree", target = "academicDegree", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "department", target = "department", qualifiedByName = "fromEntityToShortDto")
    @BeanMapping(ignoreByDefault = true)
    AccountProfileDto fromEntityToProfileDtoForClient(ExpertProfile expertProfile);

    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.kind", target = "kind")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.fullName", target = "fullName")
    @Mapping(source = "account.avatarPath", target = "avatar")
    @Mapping(source = "dob", target = "dateOfBirth")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "bio", target = "bio")
    @BeanMapping(ignoreByDefault = true)
    AccountProfileDto fromEntityToProfileDtoForClient(UserProfile userProfile);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToShortDtoForNotification")
    AccountDto fromEntityToShortDtoForNotification(Account account);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromEntityToShortDtoForNotification")
    @Named("fromEntityToShortDtoListForNotification")
    List<AccountDto> fromEntityToShortDtoListForNotification(List<Account> accounts);
}
