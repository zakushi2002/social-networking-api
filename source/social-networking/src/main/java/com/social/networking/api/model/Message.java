package com.social.networking.api.model;

import com.social.networking.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "message")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Message extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.social.networking.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Conversation conversation;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Account sender;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
}
