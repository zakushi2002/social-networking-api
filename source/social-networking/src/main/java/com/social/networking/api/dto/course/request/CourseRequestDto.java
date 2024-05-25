package com.social.networking.api.dto.course.request;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.course.CourseDto;
import lombok.Data;

@Data
public class CourseRequestDto extends InfoAdminDto {
    private CourseDto course;
    private String fullName;
    private String email;
    private String phone;
    private Integer state;
}
