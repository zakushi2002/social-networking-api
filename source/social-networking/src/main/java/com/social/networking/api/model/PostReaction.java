package com.social.networking.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "post_reaction")
@Getter
@Setter
public class PostReaction {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.social.networking.api.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;
    private Integer kind;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
}
