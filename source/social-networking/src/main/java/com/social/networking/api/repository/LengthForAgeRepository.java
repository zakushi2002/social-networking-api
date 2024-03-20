package com.social.networking.api.repository;

import com.social.networking.api.model.LengthForAge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LengthForAgeRepository extends JpaRepository<LengthForAge, Long>, JpaSpecificationExecutor<LengthForAge> {
}
