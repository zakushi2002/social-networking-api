package com.social.networking.api.repository;

import com.social.networking.api.model.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long>, JpaSpecificationExecutor<CommunityMember> {
    Optional<CommunityMember> findByAccountIdAndCommunityId(Long accountId, Long communityId);
    List<CommunityMember> findAllByAccountId(Long accountId);
    List<CommunityMember> findAllByCommunityId(Long communityId);
}
