package com.example.project3.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepo extends JpaRepository<PurchaseEntity, Long> {
}
