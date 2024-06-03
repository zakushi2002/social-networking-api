package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.course.money.CourseMoneyHistoryDto;
import com.social.networking.api.exception.BadRequestException;
import com.social.networking.api.exception.NotFoundException;
import com.social.networking.api.form.course.money.ChangeStateCourseMoneyHistoryForm;
import com.social.networking.api.form.course.money.CreateCourseMoneyHistoryForm;
import com.social.networking.api.mapper.CourseMoneyHistoryMapper;
import com.social.networking.api.model.CourseMoneyHistory;
import com.social.networking.api.model.CourseRequest;
import com.social.networking.api.model.criteria.CourseMoneyHistoryCriteria;
import com.social.networking.api.repository.CourseMoneyHistoryRepository;
import com.social.networking.api.repository.CourseRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/v1/course-money-history")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CourseMoneyHistoryController {
    @Autowired
    CourseMoneyHistoryRepository courseMoneyHistoryRepository;
    @Autowired
    CourseRequestRepository courseRequestRepository;
    @Autowired
    private CourseMoneyHistoryMapper courseMoneyHistoryMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> createCourseMoneyHistory(@Valid @RequestBody CreateCourseMoneyHistoryForm createCourseMoneyHistoryForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        CourseRequest courseRequest = courseRequestRepository.findById(createCourseMoneyHistoryForm.getCourseRequestId()).orElse(null);
        if (courseRequest == null) {
            throw new NotFoundException("[CourseMoneyHistory] Course request not found!", ErrorCode.COURSE_REQUEST_ERROR_NOT_FOUND);
        }
        CourseMoneyHistory courseMoneyHistory = courseMoneyHistoryMapper.fromCreateCourseMoneyHistoryFormToEntity(createCourseMoneyHistoryForm);
        courseMoneyHistory.setCourseRequest(courseRequest);
        courseMoneyHistory.setState(SocialNetworkingConstant.COURSE_MONEY_HISTORY_STATE_PENDING);
        courseMoneyHistoryRepository.save(courseMoneyHistory);
        apiMessageDto.setMessage("Course money history created successfully!");
        apiMessageDto.setData(courseMoneyHistory.getId());
        return apiMessageDto;
    }

    @PutMapping(value = "/change-state", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> changeStateCourseMoneyHistory(@Valid @RequestBody ChangeStateCourseMoneyHistoryForm changeStateCourseMoneyHistoryForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        CourseMoneyHistory courseMoneyHistory = courseMoneyHistoryRepository.findById(changeStateCourseMoneyHistoryForm.getId()).orElse(null);
        if (courseMoneyHistory == null) {
            throw new NotFoundException("[CourseMoneyHistory] Course money history not found!", ErrorCode.COURSE_MONEY_HISTORY_ERROR_NOT_FOUND);
        }
        if (!Objects.equals(courseMoneyHistory.getState(), SocialNetworkingConstant.COURSE_MONEY_HISTORY_STATE_PENDING)) {
            throw new BadRequestException("[CourseMoneyHistory] Course money history state is not pending!", ErrorCode.COURSE_MONEY_HISTORY_ERROR_STATE_NOT_PENDING);
        }
        courseMoneyHistory.setState(changeStateCourseMoneyHistoryForm.getState());
        courseMoneyHistoryRepository.save(courseMoneyHistory);
        apiMessageDto.setMessage("Course money history state changed successfully!");
        apiMessageDto.setData(courseMoneyHistory.getId());
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CourseMoneyHistoryDto> getCourseMoneyHistory(@PathVariable Long id) {
        ApiMessageDto<CourseMoneyHistoryDto> apiMessageDto = new ApiMessageDto<>();
        CourseMoneyHistory courseMoneyHistory = courseMoneyHistoryRepository.findById(id).orElse(null);
        if (courseMoneyHistory == null) {
            throw new NotFoundException("[CourseMoneyHistory] Course money history not found!", ErrorCode.COURSE_MONEY_HISTORY_ERROR_NOT_FOUND);
        }
        CourseMoneyHistoryDto courseMoneyHistoryDto = courseMoneyHistoryMapper.fromEntityToCourseMoneyHistoryDto(courseMoneyHistory);
        apiMessageDto.setMessage("Course money history found successfully!");
        apiMessageDto.setData(courseMoneyHistoryDto);
        return apiMessageDto;
    }

    @GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> deleteCourseMoneyHistory(@PathVariable Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        CourseMoneyHistory courseMoneyHistory = courseMoneyHistoryRepository.findById(id).orElse(null);
        if (courseMoneyHistory == null) {
            throw new NotFoundException("[CourseMoneyHistory] Course money history not found!", ErrorCode.COURSE_MONEY_HISTORY_ERROR_NOT_FOUND);
        }
        courseMoneyHistoryRepository.deleteById(id);
        apiMessageDto.setMessage("Course money history deleted successfully!");
        apiMessageDto.setData(courseMoneyHistory.getId());
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<CourseMoneyHistoryDto>> listCourseMoneyHistory(CourseMoneyHistoryCriteria courseMoneyHistoryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CourseMoneyHistoryDto>> apiMessageDto = new ApiMessageDto<>();
        Page<CourseMoneyHistory> courseMoneyHistoryPage = courseMoneyHistoryRepository.findAll(courseMoneyHistoryCriteria.getSpecification(), pageable);
        ResponseListDto<CourseMoneyHistoryDto> responseListDto = new ResponseListDto(courseMoneyHistoryMapper.fromEntityListToCourseMoneyHistoryDtoList(courseMoneyHistoryPage.getContent()), courseMoneyHistoryPage.getTotalElements(), courseMoneyHistoryPage.getTotalPages());
        apiMessageDto.setMessage("Course money history list found successfully!");
        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }
}
