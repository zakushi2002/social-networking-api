package com.social.networking.api.view.mapper;

import com.social.networking.api.model.Account;
import com.social.networking.api.view.dto.account.AccountDto;
import com.social.networking.api.view.form.account.UpdateAdminForm;
import com.social.networking.api.view.form.profile.expert.CreateExpertAccountForm;
import com.social.networking.api.view.form.profile.user.CreateUserAccountForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class})
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
}
