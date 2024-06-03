package com.social.networking.api.dto.course.money;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.course.request.CourseRequestDto;
import lombok.Data;

@Data
public class CourseMoneyHistoryDto extends InfoAdminDto {
    private Long id;
    private CourseRequestDto courseRequest;
    private Double money;
    private String description;
    private String bankName;
    private String accountNumber;
    private String accountOwner;
    private String transactionId;
    private Integer state;
}
