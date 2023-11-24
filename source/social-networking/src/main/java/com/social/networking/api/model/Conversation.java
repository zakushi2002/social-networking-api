package com.social.networking.api.model;

import com.social.networking.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "conversation")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Conversation extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.social.networking.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private Integer kind;
    @Column(columnDefinition = "LONGTEXT")
    private String lastMessage;
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ConversationAccount> accountList = new ArrayList<>();
}
