package com.example.project3.item;

import com.example.project3.shop.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByIdAndShop(Long itemId, ShopEntity shop);

    @Query("SELECT i FROM ItemEntity i WHERE i.name LIKE %:keyword% AND i.price BETWEEN :minPrice AND :maxPrice")
    List<ItemEntity> findByNameContainingAndPriceRange(
           String keyword,
           Double minPrice,
           Double maxPrice
    );




}
