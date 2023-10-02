package com.social.networking.api.repository;

import com.social.networking.api.model.ExpertProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, Long>, JpaSpecificationExecutor<ExpertProfile> {
}
