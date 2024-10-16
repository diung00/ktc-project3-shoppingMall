package com.example.project3.shop;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {

    private String name;
    private String description;
    private Category category;
    private ShopStatus status;




    public static ShopDto fromEntity(ShopEntity entity){
        ShopDto dto = new ShopDto();
        dto.name = entity.getName();;
        dto.description = entity.getDescription();
        dto.category = entity.getCategory();
        dto.status = entity.getStatus();
        return dto;
    }
}
