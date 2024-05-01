package com.social.networking.api.dto.course;

import com.social.networking.api.dto.InfoAdminDto;
import com.social.networking.api.dto.category.CategoryDto;
import com.social.networking.api.dto.profile.expert.ExpertProfileDto;
import lombok.Data;

import java.util.Date;

@Data
public class CourseDto extends InfoAdminDto {
    private String title;
    private String description;
    private ExpertProfileDto expert;
    private Integer slots;
    private Date startDate;
    private Date endDate;
    private CategoryDto topic;
    private Integer fee;
}
