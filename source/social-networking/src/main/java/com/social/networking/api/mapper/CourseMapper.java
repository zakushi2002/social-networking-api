package com.social.networking.api.mapper;

import com.social.networking.api.dto.course.CourseDto;
import com.social.networking.api.form.course.CreateCourseForm;
import com.social.networking.api.form.course.UpdateCourseForm;
import com.social.networking.api.model.Course;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ExpertProfileMapper.class, CategoryMapper.class})
public interface CourseMapper {
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "slots", target = "slots")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "fee", target = "fee")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    Course fromCreateCourseFormToEntity(CreateCourseForm createCourseForm);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "slots", target = "slots")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "fee", target = "fee")
    @BeanMapping(ignoreByDefault = true)
    void updateCourseFromCreateCourseForm(UpdateCourseForm updateCourseForm, @MappingTarget Course course);

    @Mapping(source ="id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "expert", target = "expert", qualifiedByName = "fromEntityToShortProfileDto")
    @Mapping(source = "slots", target = "slots")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "topic", target = "topic", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "fee", target = "fee")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCourseDto")
    CourseDto fromEntityToCourseDto(Course course);

    @IterableMapping(elementTargetType = CourseDto.class, qualifiedByName = "fromEntityToCourseDto")
    @Named("fromEntityListToCourseDtoList")
    List<CourseDto> fromEntityListToCourseDtoList(List<Course> courses);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "expert", target = "expert", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "slots", target = "slots")
    @Mapping(source = "topic", target = "topic", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "fee", target = "fee")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToShortDto")
    CourseDto fromEntityToShortDto(Course course);

    @IterableMapping(elementTargetType = CourseDto.class, qualifiedByName = "fromEntityToShortDto")
    @Named("fromEntityListToShortDtoList")
    List<CourseDto> fromEntityToShortDtoList(List<Course> courses);
}
