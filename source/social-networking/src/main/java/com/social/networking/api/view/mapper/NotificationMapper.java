package com.social.networking.api.view.mapper;
import com.social.networking.api.model.Notification;
import com.social.networking.api.view.dto.notification.NotificationDto;
import com.social.networking.api.view.form.notification.CreateNotificationForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class})
public interface NotificationMapper {
    @Mapping(source = "content", target = "content")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    Notification fromCreateNotificationFormToEntity(CreateNotificationForm createNotificationForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "accountTo", target = "accountTo", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "accountFrom", target = "accountFrom", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToNotificationDto")
    NotificationDto fromEntityToNotificationDto(Notification notification);

    @IterableMapping(elementTargetType = NotificationDto.class, qualifiedByName = "fromEntityToNotificationDto")
    @Named("fromEntityToNotificationDtoList")
    List<NotificationDto> fromEntityToNotificationDtoList(List<Notification> notifications);
}