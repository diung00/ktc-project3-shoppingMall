package com.example.project3.user;

import com.example.project3.user.dto.UserDto;
import com.example.project3.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("users")

public class UserController {
    private final UserService service;


    public UserController(
            UserService service
    ){
        this.service = service;
    }

    @PostMapping("/register")
    public UserDto createUser(
            @RequestBody
            UserDto dto
    ){
        return service.createUser(dto);
    }

   /* @GetMapping("/login")
    public String login(
            @RequestParam("username")
            String username,
            @RequestParam("password")
            String password
    ){

        return "login";
    }*/



    @GetMapping("/my-profile")
    public String getMyProfile(){
        return "my-profile";
    }

    @PutMapping("/update")
    public UserDto updateUser(
            @RequestBody
            UserDto dto) {
       Long updateId = service.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
       return service.updateUser(updateId, dto);

    }

    @PutMapping("/updateProfileImg")
    public UserDto updateProfileImg(

            @RequestBody
            MultipartFile image
    ) {
        Long updateId = service.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        return service.updateProfileImg(updateId,image);
    }



















}
