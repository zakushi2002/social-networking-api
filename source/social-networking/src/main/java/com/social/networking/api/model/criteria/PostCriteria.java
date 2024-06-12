package com.social.networking.api.model.criteria;

import com.social.networking.api.constant.SocialNetworkingConstant;
import com.social.networking.api.model.Account;
import com.social.networking.api.model.Category;
import com.social.networking.api.model.Post;
import com.social.networking.api.model.PostTopic;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class PostCriteria implements Serializable {
    private Long id;
    private Long accountId;
    private String accountName;
    private Integer accountKind;
    private String title;
    private String keyword;
    private Long communityId;
    private Long topicId;
    private Integer status;
    private Integer privacy;
    private Long followerId;
    private Boolean following;
    private Boolean homeSort;

    public Specification<Post> getSpecification(Map<Long, String> mapFollowingIdList, Map<Long, String> mapCommunityMemberIdList) {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getCommunityId() != null) {
                    Join<Category, Post> community = root.join("community", JoinType.INNER);
                    predicates.add(cb.equal(community.get("kind"), SocialNetworkingConstant.CATEGORY_KIND_COMMUNITY));
                    predicates.add(cb.equal(community.get("id"), getCommunityId()));
                }
                if (getTopicId() != null) {
                    Root<PostTopic> postTopicRoot = query.from(PostTopic.class);
                    predicates.add(cb.equal(postTopicRoot.get("post").get("id"), root.get("id")));
                    predicates.add(cb.equal(postTopicRoot.get("topic").get("id"), getTopicId()));
                }
                if (getPrivacy() != null) {
                    predicates.add(cb.equal(root.get("privacy"), getPrivacy()));
                }
                if (!StringUtils.isEmpty(getTitle())) {
                    predicates.add(cb.like(cb.lower(root.get("title")), "%" + getTitle().trim().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getKeyword())) {
                    predicates.add(cb.like(cb.lower(root.get("content")), "%" + getKeyword().toLowerCase() + "%"));
                }
                if (getAccountId() != null) {
                    Join<Account, Post> join = root.join("account", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getAccountId()));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (!StringUtils.isEmpty(getAccountName())) {
                    Join<Account, Post> join = root.join("account", JoinType.INNER);
                    predicates.add(cb.like(cb.lower(join.get("fullName")), "%" + getAccountName().toLowerCase() + "%"));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getAccountKind() != null) {
                    Join<Account, Post> join = root.join("account", JoinType.INNER);
                    predicates.add(cb.equal(join.get("kind"), getAccountKind()));
                    predicates.add(cb.equal(join.get("status"), SocialNetworkingConstant.STATUS_ACTIVE));
                }
                if (getFollowing() != null && getFollowing() && getFollowerId() != null) {
                    if (!mapFollowingIdList.isEmpty() || !mapCommunityMemberIdList.isEmpty()) {
                        Root<Account> accountRoot = query.from(Account.class);

                        List<Predicate> predicatesFollowing = new ArrayList<>();
                        for (Long key : mapFollowingIdList.keySet()) {
                            predicatesFollowing.add(cb.equal(accountRoot.get("id"), key));
                        }
                        Root<Category> categoryRoot = query.from(Category.class);
                        for (Long key : mapCommunityMemberIdList.keySet()) {
                            predicatesFollowing.add(cb.equal(categoryRoot.get("id"), key));
                        }
                        predicates.add(cb.or(predicatesFollowing.toArray(new Predicate[predicatesFollowing.size()])));
                        predicates.add(cb.equal(accountRoot.get("id"), root.get("account").get("id")));
                        predicates.add(cb.equal(categoryRoot.get("id"), root.get("community").get("id")));
                    } else {
                        predicates.add(cb.equal(root.get("id"), 0));
                    }
                }
                if (getHomeSort() != null && getHomeSort()) {
                    query.orderBy(cb.desc(root.get("moderatedDate")));
                } else {
                    query.orderBy(cb.desc(root.get("createdDate")));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
