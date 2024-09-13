package com.example.project3.applyForBusiness;

import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import com.example.project3.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<RequestBusinessEntity, Long> {
    boolean existsByUserAndStatus(User user, RequestStatus status);
    boolean existsByUserIdAndStatus(Long userId, RequestStatus status);
}
