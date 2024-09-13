package com.example.project3.item;

import com.example.project3.applyForBusiness.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("{userId}/shop/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    public ResponseDto createProduct(
            @PathVariable
            Long userId,
            @RequestBody
            ItemDto dto
    ) {
        itemService.createItem(userId, dto);
        ResponseDto response = new ResponseDto();
        response.setResponseMessage("Product created successfully!");
        return response;
    }

    @PutMapping("/update/{itemId}")
    public ResponseDto updateProduct(
            @PathVariable
            Long userId,
            @PathVariable
            Long itemId,
            @RequestBody
            ItemDto dto
    ){
        itemService.updateItem(userId,itemId, dto);
        ResponseDto response = new ResponseDto();
        response.setResponseMessage("Product updated successfully!");
        return response;
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseDto deleteProduct(
            @PathVariable
            Long userId,
            @PathVariable
            Long itemId
    ){
        itemService.deleteItem(userId, itemId);
        ResponseDto response = new ResponseDto();
        response.setResponseMessage("Product deleted successfully!");
        return response;
    }



}
