package com.example.project3.shop;

import com.example.project3.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    boolean existsByOwnerAndStatus(User user, Status status);
}
