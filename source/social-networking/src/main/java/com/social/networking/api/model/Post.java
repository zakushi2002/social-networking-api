package com.social.networking.api.model;

import com.social.networking.api.model.audit.Auditable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "post")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Post extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.social.networking.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
    private Integer kind;
    private Integer privacy;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostReaction> postReactions = new ArrayList<>();
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> commentList;
    private Date moderatedDate;
}
