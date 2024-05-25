package com.social.networking.api.repository;

import com.social.networking.api.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    @Transactional
    void deleteAllByIdUserAndKindAndRefId(Long idUser, Integer kind, String refId);

    Page<Notification> findAllByIdUserAndState(Long userId, Integer state, Pageable pageable);

    Page<Notification> findAllByIdUser(Long userId, Pageable pageable);

    List<Notification> findAllByIdUserAndState(Long userId, Integer state);

    Long countByIdUserAndState(Long userId, Integer state);

    @Transactional
    void deleteAllByIdUser(Long id);
}
