package com.example.project3.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        // ROLE_USER,READ,WRITE 등으로 권한을 관리한다.
        @Setter
        private String authorities;

        @Setter
        private String profileImgUrl;

}

