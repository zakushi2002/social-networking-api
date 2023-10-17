package com.social.networking.api.view.mapper;

import com.social.networking.api.model.Report;
import com.social.networking.api.view.dto.report.ReportDto;
import com.social.networking.api.view.form.report.CreateReportForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReportMapper {
    @Mapping(source = "content", target = "content")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "objectId", target = "objectId")
    @BeanMapping(ignoreByDefault = true)
    Report fromCreateReportFormToEntity(CreateReportForm createReportForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "objectId", target = "objectId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToReportDto")
    ReportDto fromEntityToReportDto(Report report);

    @IterableMapping(elementTargetType = ReportDto.class, qualifiedByName = "fromEntityToReportDto")
    @Named("fromEntityListToReportDtoList")
    List<ReportDto> fromEntityListToReportDtoList(List<Report> reports);

}
