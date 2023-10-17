package com.social.networking.api.repository;

import com.social.networking.api.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {
    Optional<Report> findByObjectIdAndKindAndStatus(Long objectId, Integer kind, Integer status);
}
