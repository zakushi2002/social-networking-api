package com.social.networking.api.repository;

import com.social.networking.api.model.CourseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRequestRepository extends JpaRepository<CourseRequest, Long>, JpaSpecificationExecutor<CourseRequest> {
}
