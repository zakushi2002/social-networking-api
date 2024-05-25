package com.social.networking.api.form.course.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateCourseRequest {
    @NotEmpty(message = "fullName cannot be empty!")
    private String fullName;
    @NotEmpty(message = "email cannot be empty!")
    @Email
    private String email;
    @NotEmpty(message = "phone cannot be empty!")
    @Size(min = 10, max = 11, message = "phone must be at least 10 characters and at most 11 characters")
    private String phone;
    @NotNull(message = "courseId cannot be null!")
    private Long courseId;
}
