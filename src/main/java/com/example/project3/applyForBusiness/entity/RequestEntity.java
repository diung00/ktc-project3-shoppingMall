package com.example.project3.applyForBusiness.entity;

import com.example.project3.applyForBusiness.RequestStatus;
import com.example.project3.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Entity
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Setter
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Setter
    private String reason; // Lý do người dùng nộp đơn chuyển đổi sang business






}
