package com.example.project3.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    Long itemId;
    Integer quantity;
    Double totalPrice;

    public static PurchaseDto fromEntity(PurchaseEntity entity){
        PurchaseDto dto = new PurchaseDto();
        dto.setItemId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setTotalPrice(entity.getTotalPrice());
        return dto;
    }
}
