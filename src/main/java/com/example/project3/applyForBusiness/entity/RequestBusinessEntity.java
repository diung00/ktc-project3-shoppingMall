package com.example.project3.applyForBusiness.entity;

import com.example.project3.BaseEntity;
import com.example.project3.applyForBusiness.RequestStatus;
import com.example.project3.user.entity.User;
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
    private User user;


    @Enumerated(EnumType.STRING)
    private RequestStatus status;


    private String reason; // Lý do người dùng nộp đơn chuyển đổi sang business






}
