package com.example.project3.user.entity;

import com.example.project3.BaseEntity;
import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import com.example.project3.purchase.PurchaseEntity;
import com.example.project3.shop.ShopEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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


        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "shop_id")
        private ShopEntity shop;


        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<RequestBusinessEntity> requests;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<PurchaseEntity> purchases;





}

