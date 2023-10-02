package com.social.networking.api.model;

import com.social.networking.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "expert_profile")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ExpertProfile extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.social.networking.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;
    private Date dob;
    private String phone;
    private String hospital;
    private String hospitalRole;
    private Integer academicDegree;
    @Column(columnDefinition = "TEXT")
    private String department;
    @Column(columnDefinition = "LONGTEXT")
    private String bio;
}
