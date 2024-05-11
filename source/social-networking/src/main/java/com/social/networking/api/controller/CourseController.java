package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.course.CourseDto;
import com.social.networking.api.form.course.CreateCourseForm;
import com.social.networking.api.form.course.HandleCourseForm;
import com.social.networking.api.form.course.UpdateCourseForm;
import com.social.networking.api.mapper.CourseMapper;
import com.social.networking.api.message.course.CourseMessage;
import com.social.networking.api.model.Category;
import com.social.networking.api.model.Course;
import com.social.networking.api.model.ExpertProfile;
import com.social.networking.api.model.criteria.CourseCriteria;
import com.social.networking.api.repository.CategoryRepository;
import com.social.networking.api.repository.CourseRepository;
import com.social.networking.api.repository.ExpertProfileRepository;
import com.social.networking.api.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/courses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CourseController extends BaseController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    ExpertProfileRepository expertProfileRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> createCourse(@Valid @RequestBody CreateCourseForm createCourseForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        ExpertProfile expertProfile = expertProfileRepository.findById(createCourseForm.getExpertId()).orElse(null);
        if (expertProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Expert not found!");
            apiMessageDto.setCode(ErrorCode.EXPERT_PROFILE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Category topic = categoryRepository.findById(createCourseForm.getTopicId()).orElse(null);
        Course course = courseMapper.fromCreateCourseFormToEntity(createCourseForm);
        course.setExpert(expertProfile);
        course.setTopic(topic);
        courseRepository.save(course);
        apiMessageDto.setMessage("Course created successfully!");
        apiMessageDto.setData(course.getId());
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> updateCourse(@Valid @RequestBody UpdateCourseForm updateCourseForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(updateCourseForm.getId()).orElse(null);
        if (course == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Course not found!");
            apiMessageDto.setCode(ErrorCode.COURSE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        courseMapper.updateCourseFromCreateCourseForm(updateCourseForm, course);
        courseRepository.save(course);
        apiMessageDto.setMessage("Course updated successfully!");
        apiMessageDto.setData(course.getId());
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> deleteCourse(@PathVariable Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Course not found!");
            apiMessageDto.setCode(ErrorCode.COURSE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        courseRepository.delete(course);
        apiMessageDto.setMessage("Course deleted successfully!");
        apiMessageDto.setData(course.getId());
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CourseDto> getCourse(@PathVariable Long id) {
        ApiMessageDto<CourseDto> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Course not found!");
            apiMessageDto.setCode(ErrorCode.COURSE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setMessage("Get a course successfully!");
        apiMessageDto.setData(courseMapper.fromEntityToCourseDto(course));
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<CourseDto>> listCourses(CourseCriteria courseCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CourseDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Course> coursePage = courseRepository.findAll(courseCriteria.getSpecification(), pageable);
        ResponseListDto<CourseDto> responseListDto = new ResponseListDto(courseMapper.fromEntityListToCourseDtoList(coursePage.getContent()), coursePage.getTotalElements(), coursePage.getTotalPages());
        apiMessageDto.setMessage("List courses successfully!");
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<CourseDto>> autoCompleteCourses(CourseCriteria courseCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CourseDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Course> coursePage = courseRepository.findAll(courseCriteria.getSpecification(), pageable);
        ResponseListDto<CourseDto> responseListDto = new ResponseListDto(courseMapper.fromEntityToShortDtoList(coursePage.getContent()), coursePage.getTotalElements(), coursePage.getTotalPages());
        apiMessageDto.setMessage("Auto complete courses successfully!");
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @PutMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> approveCourse(@Valid @RequestBody HandleCourseForm handleCourseForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(handleCourseForm.getId()).orElse(null);
        if (course == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Course not found!");
            apiMessageDto.setCode(ErrorCode.COURSE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!course.getStatus().equals(SocialNetworkingConstant.STATUS_PENDING)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Course is not pending!");
            apiMessageDto.setCode(ErrorCode.COURSE_ERROR_HANDLED);
            return apiMessageDto;
        }
        course.setStatus(SocialNetworkingConstant.STATUS_ACTIVE);
        courseRepository.save(course);
        if (isAdmin()) {
            MessageService<Course> messageService = new CourseMessage();
            messageService.createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, course, SocialNetworkingConstant.NOTIFICATION_KIND_COURSE_APPROVED);
        }
        apiMessageDto.setMessage("Course approved successfully!");
        apiMessageDto.setData(course.getId());
        return apiMessageDto;
    }

    @PutMapping(value = "/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> rejectCourse(@Valid @RequestBody HandleCourseForm handleCourseForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(handleCourseForm.getId()).orElse(null);
        if (course == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Course not found!");
            apiMessageDto.setCode(ErrorCode.COURSE_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!course.getStatus().equals(SocialNetworkingConstant.STATUS_PENDING)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Course is not pending!");
            apiMessageDto.setCode(ErrorCode.COURSE_ERROR_HANDLED);
            return apiMessageDto;
        }
        courseRepository.delete(course);
        apiMessageDto.setMessage("Course rejected successfully!");
        apiMessageDto.setData(handleCourseForm.getId());
        return apiMessageDto;
    }
}
