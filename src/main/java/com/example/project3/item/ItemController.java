package com.example.project3.item;

import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    public ItemDto createProduct(
            @RequestBody
            ItemDto dto
    ) {
        return itemService.createItem(dto);
    }

    @PutMapping("/update/{itemId}")
    public ItemDto updateItem(
            @PathVariable
            Long itemId,
            @RequestBody
            ItemDto dto
    ){
        return itemService.updateItem(itemId, dto);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ItemDto> deleteItem(
            @PathVariable
            Long itemId
    ){
        return ResponseEntity.ok().build();
    }



}
