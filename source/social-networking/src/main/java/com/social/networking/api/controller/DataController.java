package com.social.networking.api.controller;

import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.model.LengthForAge;
import com.social.networking.api.model.WeightForAge;
import com.social.networking.api.model.criteria.LengthForAgeCriteria;
import com.social.networking.api.model.criteria.WeightForAgeCriteria;
import com.social.networking.api.repository.LengthForAgeRepository;
import com.social.networking.api.repository.WeightForAgeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/data")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class DataController {
    @Autowired
    WeightForAgeRepository weightForAgeRepository;
    @Autowired
    LengthForAgeRepository lengthForAgeRepository;

    @GetMapping(value = "/list-weight-for-age", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<WeightForAge>> listWeightForAge(WeightForAgeCriteria criteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<WeightForAge>> apiMessageDto = new ApiMessageDto<>();
        Page<WeightForAge> page = weightForAgeRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto<WeightForAge> responseListObj = new ResponseListDto(page.getContent(), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List weight for age success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-length-for-age", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<LengthForAge>> listLengthForAge(LengthForAgeCriteria criteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<LengthForAge>> apiMessageDto = new ApiMessageDto<>();
        Page<LengthForAge> page = lengthForAgeRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto<LengthForAge> responseListObj = new ResponseListDto(page.getContent(), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List length for age success.");
        return apiMessageDto;
    }
}
