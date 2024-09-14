package com.example.project3.item;

import com.example.project3.applyForBusiness.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("shop/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/create")
    public ResponseDto createProduct(
            @RequestBody
            ItemDto dto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // username form token
        itemService.createItem(dto, currentUsername);
        ResponseDto response = new ResponseDto();
        response.setResponseMessage("Product created successfully!");
        return response;
    }

    @PutMapping("/update/{itemId}")
    public ResponseDto updateProduct(
            @PathVariable
            Long itemId,
            @RequestBody
            ItemDto dto
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // username form token
        itemService.updateItem(itemId, dto, currentUsername);
        ResponseDto response = new ResponseDto();
        response.setResponseMessage("Product updated successfully!");
        return response;
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseDto deleteProduct(
            @PathVariable
            Long itemId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // username form token
        itemService.deleteItem(itemId, currentUsername);
        ResponseDto response = new ResponseDto();
        response.setResponseMessage("Product deleted successfully!");
        return response;
    }



}
