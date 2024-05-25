package com.social.networking.api.mapper;

import com.social.networking.api.dto.course.request.CourseRequestDto;
import com.social.networking.api.form.course.request.CreateCourseRequest;
import com.social.networking.api.model.CourseRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CourseMapper.class})
public interface CourseRequestMapper {
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @BeanMapping(ignoreByDefault = true)
    CourseRequest fromCreateCourseRequestToEntity(CreateCourseRequest createCourseRequest);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "course", target = "course", qualifiedByName = "fromEntityToShortDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCourseRequestDto")
    CourseRequestDto fromEntityToCourseRequestDto(CourseRequest courseRequest);

    @IterableMapping(elementTargetType = CourseRequestDto.class, qualifiedByName = "fromEntityToCourseRequestDto")
    @Named("fromEntityListToCourseRequestDtoList")
    List<CourseRequestDto> fromEntityListToCourseRequestDtoList(List<CourseRequest> courseRequests);
}
