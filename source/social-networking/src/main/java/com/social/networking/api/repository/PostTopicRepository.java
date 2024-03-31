package com.social.networking.api.repository;

import com.social.networking.api.model.PostTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PostTopicRepository extends JpaRepository<PostTopic, Long>, JpaSpecificationExecutor<PostTopic> {
    List<PostTopic> findAllByPostId(Long postId);
}
