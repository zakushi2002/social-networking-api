package com.social.networking.api.repository;

import com.social.networking.api.model.CourseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CourseRequestRepository extends JpaRepository<CourseRequest, Long>, JpaSpecificationExecutor<CourseRequest> {
    Optional<CourseRequest> findByCourseIdAndEmail(Long courseId, String email);
}
