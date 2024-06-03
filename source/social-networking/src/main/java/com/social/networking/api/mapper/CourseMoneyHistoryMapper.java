package com.social.networking.api.mapper;

import com.social.networking.api.dto.course.money.CourseMoneyHistoryDto;
import com.social.networking.api.form.course.money.CreateCourseMoneyHistoryForm;
import com.social.networking.api.model.CourseMoneyHistory;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CourseRequestMapper.class})
public interface CourseMoneyHistoryMapper {
    @Mapping(source = "money", target = "money")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "accountOwner", target = "accountOwner")
    @Mapping(source = "transactionId", target = "transactionId")
    @BeanMapping(ignoreByDefault = true)
    CourseMoneyHistory fromCreateCourseMoneyHistoryFormToEntity(CreateCourseMoneyHistoryForm createCourseMoneyHistoryForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "courseRequest", target = "courseRequest", qualifiedByName = "fromEntityToCourseRequestDto")
    @Mapping(source = "money", target = "money")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "accountOwner", target = "accountOwner")
    @Mapping(source = "transactionId", target = "transactionId")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCourseMoneyHistoryDto")
    CourseMoneyHistoryDto fromEntityToCourseMoneyHistoryDto(CourseMoneyHistory courseMoneyHistory);

    @IterableMapping(elementTargetType = CourseMoneyHistoryDto.class, qualifiedByName = "fromEntityToCourseMoneyHistoryDto")
    @Named("fromEntityListToCourseMoneyHistoryDtoList")
    List<CourseMoneyHistoryDto> fromEntityListToCourseMoneyHistoryDtoList(List<CourseMoneyHistory> courseMoneyHistories);
}
