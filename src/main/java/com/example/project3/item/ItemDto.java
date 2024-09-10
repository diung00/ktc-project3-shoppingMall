package com.example.project3.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;

    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;
}
