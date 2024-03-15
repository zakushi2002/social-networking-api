package com.social.networking.api.repository;

import com.social.networking.api.model.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long>, JpaSpecificationExecutor<CommunityMember> {
}
