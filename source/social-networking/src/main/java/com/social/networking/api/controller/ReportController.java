package com.social.networking.api.controller;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.exception.BadRequestException;
import com.social.networking.api.exception.NotFoundException;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Comment;
import com.social.networking.api.model.Post;
import com.social.networking.api.model.Report;
import com.social.networking.api.model.criteria.ReportCriteria;
import com.social.networking.api.repository.AccountRepository;
import com.social.networking.api.repository.CommentRepository;
import com.social.networking.api.repository.PostRepository;
import com.social.networking.api.repository.ReportRepository;
import com.social.networking.api.dto.ApiMessageDto;
import com.social.networking.api.dto.ErrorCode;
import com.social.networking.api.dto.ResponseListDto;
import com.social.networking.api.dto.report.ReportDto;
import com.social.networking.api.form.report.HandleReportForm;
import com.social.networking.api.form.report.CreateReportForm;
import com.social.networking.api.mapper.ReportMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/report")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ReportController extends BaseController {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ReportMapper reportMapper;
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REPORT_C')")
    @Transactional
    public ApiMessageDto<Long> createReport(@Valid @RequestBody CreateReportForm createReportForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        if (createReportForm.getKind().equals(SocialNetworkingConstant.REPORT_KIND_POST)) {
            Post post = postRepository.findById(createReportForm.getObjectId()).orElse(null);
            if (post == null) {
                throw new NotFoundException("[Report] Post not found!", ErrorCode.POST_ERROR_NOT_FOUND);
            }
        } else if (createReportForm.getKind().equals(SocialNetworkingConstant.REPORT_KIND_COMMENT)) {
            Comment comment = commentRepository.findById(createReportForm.getObjectId()).orElse(null);
            if (comment == null) {
                throw new NotFoundException("[Report] Comment not found!", ErrorCode.COMMENT_ERROR_NOT_FOUND);
            }
        } else if (createReportForm.getKind().equals(SocialNetworkingConstant.REPORT_KIND_ACCOUNT)) {
            if (createReportForm.getObjectId().equals(getCurrentUser())) {
                throw new BadRequestException("[Report] You cannot report yourself!", ErrorCode.REPORT_ERROR_REPORT_YOURSELF);
            }
            Account account = accountRepository.findById(createReportForm.getObjectId()).orElse(null);
            if (account == null) {
                throw new NotFoundException("[Report] Account not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            }
        }
        Report report = reportRepository.findByObjectIdAndKindAndStatus(createReportForm.getObjectId(), createReportForm.getKind(), SocialNetworkingConstant.STATUS_PENDING).orElse(null);
        if (report != null) {
            if (!report.getContent().contains(createReportForm.getContent())) {
                report.setContent(report.getContent() + "\n" + createReportForm.getContent());
            }
            report.setQuantity(report.getQuantity() + 1);
        } else {
            report = reportMapper.fromCreateReportFormToEntity(createReportForm);
            report.setStatus(SocialNetworkingConstant.STATUS_PENDING);
        }
        reportRepository.save(report);
        apiMessageDto.setData(report.getId());
        apiMessageDto.setMessage("Report successfully.");
        return apiMessageDto;
    }

    @PutMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REPORT_APPROVE')")
    @Transactional
    public ApiMessageDto<Long> approveReport(@Valid @RequestBody HandleReportForm handleReportForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Report report = reportRepository.findById(handleReportForm.getId()).orElse(null);
        if (report == null) {
            throw new NotFoundException("[Report] Report not found!", ErrorCode.REPORT_ERROR_NOT_FOUND);
        }
        if (!report.getStatus().equals(SocialNetworkingConstant.STATUS_PENDING)) {
            throw new BadRequestException("[Report] Report has been approved or rejected!", ErrorCode.REPORT_ERROR_APPROVED_OR_REJECTED);
        }
        if (report.getKind().equals(SocialNetworkingConstant.REPORT_KIND_POST)) {
            Post post = postRepository.findById(report.getObjectId()).orElse(null);
            if (post == null) {
                throw new NotFoundException("[Report] Post not found!", ErrorCode.POST_ERROR_NOT_FOUND);
            }
            postRepository.deleteById(report.getObjectId());
        } else if (report.getKind().equals(SocialNetworkingConstant.REPORT_KIND_COMMENT)) {
            Comment comment = commentRepository.findById(report.getObjectId()).orElse(null);
            if (comment == null) {
                throw new NotFoundException("[Report] Comment not found!", ErrorCode.COMMENT_ERROR_NOT_FOUND);
            }
            commentRepository.deleteById(report.getObjectId());
        } else if (report.getKind().equals(SocialNetworkingConstant.REPORT_KIND_ACCOUNT)) {
            Account account = accountRepository.findById(report.getObjectId()).orElse(null);
            if (account == null) {
                throw new NotFoundException("[Report] Account not found!", ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            }
            account.setStatus(SocialNetworkingConstant.STATUS_LOCK);
            accountRepository.save(account);
        }
        report.setStatus(SocialNetworkingConstant.STATUS_ACTIVE);
        reportRepository.save(report);
        apiMessageDto.setMessage("Approve successfully");
        apiMessageDto.setData(report.getId());
        return apiMessageDto;
    }

    @PutMapping(value = "/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REPORT_REJECT')")
    @Transactional
    public ApiMessageDto<Long> rejectReport(@Valid @RequestBody HandleReportForm handleReportForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Report report = reportRepository.findById(handleReportForm.getId()).orElse(null);
        if (report == null) {
            throw new NotFoundException("[Report] Report not found!", ErrorCode.REPORT_ERROR_NOT_FOUND);
        }
        if (!report.getStatus().equals(SocialNetworkingConstant.STATUS_PENDING)) {
            throw new BadRequestException("[Report] Report has been approved or rejected!", ErrorCode.REPORT_ERROR_APPROVED_OR_REJECTED);
        }
        report.setStatus(SocialNetworkingConstant.STATUS_DELETE);
        reportRepository.save(report);
        apiMessageDto.setMessage("Reject successfully.");
        apiMessageDto.setData(report.getId());
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REPORT_L')")
    public ApiMessageDto<ResponseListDto<ReportDto>> listReport(ReportCriteria reportCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ReportDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Report> page = reportRepository.findAll(reportCriteria.getSpecification(), pageable);
        ResponseListDto<ReportDto> responseListDto = new ResponseListDto(reportMapper.fromEntityListToReportDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List report success.");
        return apiMessageDto;
    }
}
