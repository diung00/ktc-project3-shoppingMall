package com.example.project3.shop.shopOpeningRequest;

import com.example.project3.shop.ShopStatus;
import lombok.Getter;

@Getter
public class ShopRequestDto {

    ShopStatus status;
    String reason;

    public static ShopRequestDto fromEntity(ShopRequestEntity entity){
        ShopRequestDto dto = new ShopRequestDto();
        dto.status = entity.getStatus();
        dto.reason = entity.getReason();
        return dto;
    }

}
