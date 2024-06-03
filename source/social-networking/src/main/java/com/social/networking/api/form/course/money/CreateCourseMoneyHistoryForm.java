package com.social.networking.api.form.course.money;

import lombok.Data;

@Data
public class CreateCourseMoneyHistoryForm {
    private Long courseRequestId;
    private Double money;
    private String description;
    private String bankName;
    private String accountNumber;
    private String accountOwner;
    private String transactionId;
}
