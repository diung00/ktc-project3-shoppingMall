package com.example.project3.item;

import lombok.*;


@Getter
@NoArgsConstructor
@ToString
@Setter
@AllArgsConstructor
public class ItemDto {

    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;




    public static ItemDto fromEntity(ItemEntity itemEntity) {
        ItemDto itemDto = new ItemDto();
        itemDto.name = itemEntity.getName();
        itemDto.description = itemEntity.getDescription();
        itemDto.imageUrl = itemEntity.getImageUrl();
        itemDto.price = itemEntity.getPrice();
        itemDto.stock = itemEntity.getStock();

        return itemDto;

    }
}
