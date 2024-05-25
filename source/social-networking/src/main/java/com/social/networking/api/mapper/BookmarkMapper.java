package com.social.networking.api.mapper;

import com.social.networking.api.model.Bookmark;
import com.social.networking.api.dto.post.bookmark.BookmarkDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PostMapper.class})
public interface BookmarkMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "post", target = "postDto", qualifiedByName = "fromEntityToPostDtoForBookmark")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToBookmarkDto")
    BookmarkDto fromEntityToBookmarkDto(Bookmark bookmark);

    @IterableMapping(elementTargetType = BookmarkDto.class, qualifiedByName = "fromEntityToBookmarkDto")
    List<BookmarkDto> fromEntitiesToBookmarkDtoList(List<Bookmark> bookmarks);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "post", target = "postDto", qualifiedByName = "fromEntityToPostDtoForBookmarkAdmin")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToBookmarkDtoForAdmin")
    BookmarkDto fromEntityToBookmarkDtoForAdmin(Bookmark bookmark);

    @IterableMapping(elementTargetType = BookmarkDto.class, qualifiedByName = "fromEntityToBookmarkDtoForAdmin")
    @Named("fromEntitiesToBookmarkDtoListForAdmin")
    List<BookmarkDto> fromEntitiesToBookmarkDtoListForAdmin(List<Bookmark> bookmarks);
}
