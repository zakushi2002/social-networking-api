package com.social.networking.api.model;

import com.social.networking.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "course_money_history")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class CourseMoneyHistory extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.social.networking.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_request_id")
    private CourseRequest courseRequest;
    private Double money;
    private String description;
    private String bankName;
    private String accountNumber;
    private String accountOwner;
    private String transactionId;
    private Integer state;
}
