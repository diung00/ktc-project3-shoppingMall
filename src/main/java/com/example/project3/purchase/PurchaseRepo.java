package com.example.project3.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepo extends JpaRepository<PurchaseEntity, Long> {

    List<PurchaseEntity> findAllByUserId(Long userId);

}
