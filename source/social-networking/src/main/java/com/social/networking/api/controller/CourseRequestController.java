package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.course.request.CourseRequestDto;
import com.social.networking.api.exception.BadRequestException;
import com.social.networking.api.exception.NotFoundException;
import com.social.networking.api.form.course.request.CreateCourseRequest;
import com.social.networking.api.mapper.CourseRequestMapper;
import com.social.networking.api.model.Course;
import com.social.networking.api.model.CourseRequest;
import com.social.networking.api.model.criteria.CourseRequestCriteria;
import com.social.networking.api.repository.CourseRepository;
import com.social.networking.api.repository.CourseRequestRepository;
import com.social.networking.api.service.SocialNetworkingApiService;
import com.social.networking.api.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/v1/course-request")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CourseRequestController extends BaseController {
    @Autowired
    CourseRequestRepository courseRequestRepository;
    @Autowired
    CourseRequestMapper courseRequestMapper;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    SocialNetworkingApiService socialNetworkingApiService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> createCourseRequest(@Valid @RequestBody CreateCourseRequest createCourseRequest, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(createCourseRequest.getCourseId()).orElse(null);
        if (course == null) {
            throw new NotFoundException("[CourseRequest] Course not found!", ErrorCode.COURSE_ERROR_NOT_FOUND);
        }
        CourseRequest checkDuplicate = courseRequestRepository.findByCourseIdAndEmail(createCourseRequest.getCourseId(), createCourseRequest.getEmail()).orElse(null);
        if (checkDuplicate != null) {
            throw new BadRequestException("[CourseRequest] Course request already exists!", ErrorCode.COURSE_REQUEST_ERROR_DUPLICATE);
        }
        CourseRequest courseRequest = courseRequestMapper.fromCreateCourseRequestToEntity(createCourseRequest);
        courseRequest.setCourse(course);
        if (!Objects.equals(course.getFee(), 0) && !Objects.equals(course.getSlots(), 0)) {
            courseRequest.setStatus(SocialNetworkingConstant.STATUS_PENDING);
        }
        Map<String, Object> variables = getVariables(createCourseRequest, course);
        String subjectEmail = "[" + course.getTopic().getName() + "] " + course.getTitle();
        socialNetworkingApiService.sendEmail(createCourseRequest.getEmail(), variables, subjectEmail);
        courseRequestRepository.save(courseRequest);
        apiMessageDto.setMessage("Course request created successfully.");
        apiMessageDto.setData(courseRequest.getId());
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CourseRequestDto> getCourseRequest(@PathVariable Long id) {
        ApiMessageDto<CourseRequestDto> apiMessageDto = new ApiMessageDto<>();
        CourseRequest courseRequest = courseRequestRepository.findById(id).orElse(null);
        if (courseRequest == null) {
            throw new NotFoundException("[CourseRequest] Course request not found!", ErrorCode.COURSE_REQUEST_ERROR_NOT_FOUND);
        }
        CourseRequestDto courseRequestDto = courseRequestMapper.fromEntityToCourseRequestDto(courseRequest);
        apiMessageDto.setMessage("Course request found successfully.");
        apiMessageDto.setData(courseRequestDto);
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> deleteCourseRequest(@PathVariable Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        CourseRequest courseRequest = courseRequestRepository.findById(id).orElse(null);
        if (courseRequest == null) {
            throw new NotFoundException("[CourseRequest] Course request not found!", ErrorCode.COURSE_REQUEST_ERROR_NOT_FOUND);
        }
        courseRequestRepository.delete(courseRequest);
        apiMessageDto.setMessage("Course request deleted successfully.");
        apiMessageDto.setData(id);
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<CourseRequestDto>> listCourseRequest(CourseRequestCriteria criteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CourseRequestDto>> apiMessageDto = new ApiMessageDto<>();
        Page<CourseRequest> courseRequestPage = courseRequestRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto<CourseRequestDto> responseListDto = new ResponseListDto(courseRequestMapper.fromEntityListToCourseRequestDtoList(courseRequestPage.getContent()), courseRequestPage.getTotalElements(), courseRequestPage.getTotalPages());
        apiMessageDto.setMessage("Course request list found successfully.");
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    private Map<String, Object> getVariables(CreateCourseRequest createCourseRequest, Course course) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("fullName", createCourseRequest.getFullName());
        variables.put("email", createCourseRequest.getEmail());
        variables.put("phone", createCourseRequest.getPhone());
        variables.put("courseTitle", course.getTitle());
        variables.put("courseTopic", course.getTopic().getName());
        variables.put("courseFee", course.getFee());
        variables.put("courseSlots", course.getSlots());
        variables.put("courseDescription", course.getDescription());
        variables.put("courseStartDate", DateUtils.formatDate(course.getStartDate()));
        variables.put("courseEndDate", course.getEndDate());
        double diffInHours = (double) (course.getEndDate().getTime() - course.getStartDate().getTime()) / (60 * 60 * 1000);
        int totalMinute = (int) diffInHours * 60;
        variables.put("courseDuration", totalMinute);
        variables.put("courseJoinUrl", course.getJoinUrl());
        variables.put("expertName", course.getExpert().getAccount().getFullName());
        variables.put("expertEmail", course.getExpert().getAccount().getEmail());
//        variables.put("expertPhone", course.getExpert().getPhone());
//        variables.put("expertAvatar", course.getExpert().getAccount().getAvatarPath());
//        variables.put("expertHospital", course.getExpert().getHospital().getName());
//        variables.put("expertHospitalRole", course.getExpert().getHospitalRole().getName());
//        variables.put("expertAcademicDegree", course.getExpert().getAcademicDegree().getName());
//        variables.put("expertDepartment", course.getExpert().getDepartment().getName());
        return variables;
    }
}
