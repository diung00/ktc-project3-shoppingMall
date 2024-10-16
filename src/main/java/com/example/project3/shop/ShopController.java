package com.example.project3.shop;

import com.example.project3.item.ItemDto;
import com.example.project3.shop.shopOpeningRequest.ShopRequestDto;
import com.example.project3.shop.shopOpeningRequest.ShopRequestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shops")
@RequiredArgsConstructor
public class ShopController {
    @Autowired
    ShopService shopService;



    @PutMapping("/update")
    public ShopDto updateShop(
            @RequestBody
            ShopDto shopDto
    ){
        return shopService.updateShop(shopDto);
    }

    @PostMapping("/requestOpenShop")
    public ShopRequestDto requestOpenShop(
            @RequestBody
            ShopRequestDto requestDto
    ) {
        return shopService.requestOpenShop(requestDto);
    }

    @PostMapping("/requestCloseShop")
    public ResponseEntity<ShopRequestDto> requestCloseShop(
            @RequestBody
            ShopRequestDto requestDto

    ){
        shopService.requestCloseShop(requestDto);
        return ResponseEntity.ok(requestDto);
    }

}
