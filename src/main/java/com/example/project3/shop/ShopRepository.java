package com.example.project3.shop;

import com.example.project3.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    boolean existsByOwnerAndStatus(UserEntity user, ShopStatus status);

    List<ShopEntity> findByNameContainingOrderByIdDesc(String name);

    List<ShopEntity> findByCategoryOrderByIdDesc(Category category);

    Optional<ShopEntity> findByOwnerId(Long ownerId);

}
