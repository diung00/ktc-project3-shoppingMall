package com.example.project3.jwt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";  // mật khẩu gốc
        String encodedPassword = encoder.encode(rawPassword);  // mã hóa mật khẩu

        System.out.println(encodedPassword);  // In ra mật khẩu đã mã hóa
    }
}
