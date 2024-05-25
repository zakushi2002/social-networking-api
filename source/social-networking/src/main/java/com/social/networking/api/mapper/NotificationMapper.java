package com.social.networking.api.mapper;

import com.social.networking.api.form.notification.UpdateNotificationForm;
import com.social.networking.api.model.Notification;
import com.social.networking.api.dto.notification.NotificationDto;
import com.social.networking.api.form.notification.CreateNotificationForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, AnnouncementMapper.class})
public interface NotificationMapper {
    @Mapping(source = "content", target = "content")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "objectId", target = "objectId")
    @BeanMapping(ignoreByDefault = true)
    Notification fromCreateNotificationFormToEntity(CreateNotificationForm createNotificationForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "objectId", target = "objectId")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source ="createdBy", target = "createdBy")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToNotificationDto")
    NotificationDto fromEntityToNotificationDto(Notification notification);

    @IterableMapping(elementTargetType = NotificationDto.class, qualifiedByName = "fromEntityToNotificationDto")
    @Named("fromEntityToNotificationDtoList")
    List<NotificationDto> fromEntityToNotificationDtoList(List<Notification> notifications);

    @Mapping(source = "id", target = "id")
    @Mapping(source ="objectId", target = "objectId")
    // @Mapping(source = "announcements", target = "announcements", qualifiedByName = "fromEntityToDtoList")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToNotificationDtoWithAnnouncements")
    NotificationDto fromEntityToNotificationDtoWithAnnouncements(Notification notification);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "idUser", target = "idUser")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "refId", target = "refId")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "content", target = "content")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToNotiDto")
    NotificationDto fromEntityToNotificatonDto(Notification notification);

    @IterableMapping(elementTargetType = NotificationDto.class, qualifiedByName = "fromEntityToNotiDto")
    List<NotificationDto> fromEntityToNotiListDto(List<Notification> notifications);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "idUser", target = "idUser")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "refId", target = "refId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToNotiDtoAuto")
    NotificationDto fromEntityToNotificatonDtoAuto(Notification notification);
    @IterableMapping(elementTargetType = NotificationDto.class, qualifiedByName = "fromEntityToNotiDtoAuto")
    List<NotificationDto> fromEntityToNotiListDtoAuto(List<Notification> notifications);

    @Mapping(source = "idUser", target = "idUser")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "refId", target = "refId")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    void fromUpdateNotiToEntity(UpdateNotificationForm updateNotificationForm, @MappingTarget Notification notification);
}
