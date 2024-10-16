package com.example.project3.applyForBusiness;

import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import com.example.project3.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<RequestBusinessEntity, Long> {
    boolean existsByUserAndStatus(UserEntity user, RequestStatus status);
    boolean existsByUserIdAndStatus(Long userId, RequestStatus status);
}
