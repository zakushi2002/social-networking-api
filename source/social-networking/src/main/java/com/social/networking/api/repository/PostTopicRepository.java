package com.social.networking.api.repository;

import com.social.networking.api.model.PostTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostTopicRepository extends JpaRepository<PostTopic, Long>, JpaSpecificationExecutor<PostTopic> {
}
