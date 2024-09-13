package com.example.project3.shop;

import com.example.project3.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {

    private String name;
    private String description;
    private String category;

    public ShopDto(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category.name();
    }
}
