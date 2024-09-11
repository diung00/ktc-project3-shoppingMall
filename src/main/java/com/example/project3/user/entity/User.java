package com.example.project3.user.entity;

import com.example.project3.BaseEntity;
import com.example.project3.applyForBusiness.entity.RequestEntity;
import com.example.project3.shop.ShopEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User extends BaseEntity {

        private String username;
        private String password;
        private String nickname;
        private String name;
        private Integer age;
        private String email;
        private String phone;
        private String authorities;
        private String profileImgUrl;


        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "shop_id")
        private ShopEntity shop;


        @OneToMany(mappedBy = "user")
        @JsonManagedReference
        private List<RequestEntity> requests;





}

