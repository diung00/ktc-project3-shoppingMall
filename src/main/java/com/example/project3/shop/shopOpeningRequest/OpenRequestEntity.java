package com.example.project3.shop.shopOpeningRequest;

import com.example.project3.BaseEntity;
import com.example.project3.shop.Status;
import com.example.project3.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OpenRequestEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String reason;
}
