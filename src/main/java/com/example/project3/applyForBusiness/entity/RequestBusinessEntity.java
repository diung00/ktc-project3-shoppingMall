package com.example.project3.applyForBusiness.entity;

import com.example.project3.BaseEntity;
import com.example.project3.applyForBusiness.RequestStatus;
import com.example.project3.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class RequestBusinessEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;


    @Enumerated(EnumType.STRING)
    private RequestStatus status;


    private String reason;






}
