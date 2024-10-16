package com.example.project3.user;

import com.example.project3.item.ItemDto;
import com.example.project3.jwt.dto.JwtRequestDto;
import com.example.project3.jwt.dto.JwtResponseDto;
import com.example.project3.shop.ShopDto;
import com.example.project3.user.dto.UserCreateDto;
import com.example.project3.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;


    @PostMapping("/signup")
    public UserDto createUser(
            @RequestBody
            UserCreateDto dto
    ){
        return service.createUser(dto);
    }

    @PostMapping("login")
    public JwtResponseDto login (
            @RequestBody
            JwtRequestDto dto
    ){
        return service.login(dto);
    }

    @GetMapping("/myProfile")
    public UserDto getMyProfile(){

        return service.readUser();
    }

    @PutMapping("/update")
    public UserDto updateUser(
            @RequestBody
            UserDto dto
    ) {
       return service.updateUser(dto);

    }

    @PutMapping("/updateProfileImg")
    public String updateProfileImg(

            @RequestBody
            MultipartFile image
    ) {
        return service.updateProfileImg(image);
    }

    @GetMapping("searchShopByName")
    public List<ShopDto> searchShopByName(
            @RequestBody
            String shopName
    ){
        return service.searchShop(shopName);
    }

    @GetMapping("searchShopByCategory")
    public List<ShopDto> searchShopByCategory(
            @RequestBody
            String category
    ){
        return service.searchShop(category);
    }

    @GetMapping("findItem")
    public List<ItemDto> findItem(
            @RequestBody
            String key,
            @RequestBody
            Double minPrice,
            @RequestBody
            Double maxPrice
    ){
      return service.findItem(key, minPrice, maxPrice);
    }
























}
