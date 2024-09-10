package com.example.project3.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {

    private String shopName;
    private String shopDecription;
    private String shopCategory;
    private String status;
}
