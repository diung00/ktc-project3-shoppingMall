package com.example.project3.user.dto;

import com.example.project3.user.entity.UserEntity;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor

public class UserDto {

    private Long id;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private String nickname;
    @Setter
    private String name;
    @Setter
    private Integer age;
    @Setter
    private String email;
    @Setter
    private String phone;

    @Setter
    private String profileImgUrl;


    public static UserDto fromEntity(UserEntity entity){
        UserDto dto = new UserDto();
        dto.id = entity.getId();
        dto.username = entity.getUsername();
        dto.password = "hidden";
        dto.nickname = entity.getNickname();
        dto.name = entity.getName();
        dto.age = entity.getAge();
        dto.email = entity.getEmail();
        dto.phone = entity.getPhone();
        return dto;
    }
}
