package com.social.networking.api.dto.course;

import lombok.Data;

import java.util.Date;

@Data
public class CourseNotificationMessage {
    private Long notificationId;
    private Long courseId;
    private String courseTitle;
    private Long expertId;
    private String expertName;
    private Date startDate;
    private Date endDate;
}
