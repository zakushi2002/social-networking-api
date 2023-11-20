package com.social.networking.api.view.mapper;

import com.social.networking.api.model.Announcement;
import com.social.networking.api.view.dto.notification.announcement.AnnouncementDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NotificationMapper.class})
public interface AnnouncementMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "notification", target = "notification", qualifiedByName = "fromEntityToNotificationDto")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    AnnouncementDto fromEntityToDto(Announcement announcement);

    @IterableMapping(elementTargetType = AnnouncementDto.class, qualifiedByName = "fromEntityToDto")
    @Named("fromEntityToDtoList")
    List<AnnouncementDto> fromEntityToDtoList(List<Announcement> announcements);
}
