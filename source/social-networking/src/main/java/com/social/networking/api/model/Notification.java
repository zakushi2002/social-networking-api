package com.social.networking.api.model;

import com.social.networking.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "notification")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Notification extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private Integer kind;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Account accountTo;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Account accountFrom;
}
