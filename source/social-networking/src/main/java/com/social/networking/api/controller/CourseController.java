package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.course.CourseDto;
import com.social.networking.api.dto.course.CourseNotificationMessage;
import com.social.networking.api.form.course.CreateCourseForm;
import com.social.networking.api.form.course.HandleCourseForm;
import com.social.networking.api.form.course.UpdateCourseForm;
import com.social.networking.api.form.notification.NotificationService;
import com.social.networking.api.mapper.CourseMapper;
import com.social.networking.api.model.Category;
import com.social.networking.api.model.Course;
import com.social.networking.api.model.ExpertProfile;
import com.social.networking.api.model.Notification;
import com.social.networking.api.model.criteria.CourseCriteria;
import com.social.networking.api.repository.CategoryRepository;
import com.social.networking.api.repository.CourseRepository;
import com.social.networking.api.repository.ExpertProfileRepository;
import com.social.networking.api.repository.NotificationRepository;
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
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationService notificationService;

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
        createNotificationAndSendMessage(SocialNetworkingConstant.NOTIFICATION_STATE_SENT, course, SocialNetworkingConstant.NOTIFICATION_KIND_COURSE_APPROVED);
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

    /**
     * Creates a JSON message for the given course and notification.
     *
     * @param course       the course for which to create the message
     * @param notification the notification for which to create the message
     * @return the JSON message
     */
    private String getJsonMessage(Course course, Notification notification) {
        CourseNotificationMessage courseNotificationMessage = new CourseNotificationMessage();
        courseNotificationMessage.setNotificationId(notification.getId());
        courseNotificationMessage.setExpertId(course.getExpert().getId());
        courseNotificationMessage.setCourseId(course.getId());
        courseNotificationMessage.setCourseTitle(course.getTitle());
        courseNotificationMessage.setExpertName(course.getExpert().getAccount().getFullName());
        courseNotificationMessage.setStartDate(course.getStartDate());
        courseNotificationMessage.setEndDate(course.getEndDate());
        return notificationService.convertObjectToJson(courseNotificationMessage);
    }

    /**
     * Creates a notification for the given course and notification kind.
     *
     * @param course            the course for which to create the notification
     * @param notificationState the state of the notification
     * @param notificationKind  the kind of notification to create
     * @param accountId         the ID of the account for which to create the notification
     * @return the created notification
     */
    private Notification createNotification(Course course, Integer notificationState, Integer notificationKind, Long accountId) {
        Notification notification = notificationService.createNotification(notificationState, notificationKind);
        String jsonMessage = getJsonMessage(course, notification);
        notification.setIdUser(accountId);
        notification.setContent(jsonMessage);
        if (notificationKind.equals(SocialNetworkingConstant.NOTIFICATION_KIND_COURSE_APPROVED)) {
            notificationRepository.deleteAllByIdUserAndKindAndRefId(accountId, SocialNetworkingConstant.NOTIFICATION_KIND_COURSE_APPROVED, course.getId().toString());
            notification.setRefId(course.getId().toString());
        }
        return notification;
    }


    /**
     * Creates a notification and sends a message for the given course and notification kind.
     *
     * @param notificationState the state of the notification
     * @param course            the course for which to create the notification
     * @param notificationKind  the kind of notification to create
     */
    private void createNotificationAndSendMessage(Integer notificationState, Course course, Integer notificationKind) {
        List<Notification> notifications = new ArrayList<>();
        if (isExpert()) {
            if (course.getExpert() != null) {
                // Creates a notification for the given course and notification kind
                Notification notification = createNotification(course, notificationState, notificationKind, course.getExpert().getAccount().getId());
                notifications.add(notification);
            }
            // Saves the notifications to the database
            notificationRepository.saveAll(notifications);
            // Sends a message for each notification
            for (Notification notification : notifications) {
                notificationService.sendMessage(notification.getContent(), notificationKind, notification.getIdUser());
            }
        }
    }
}
