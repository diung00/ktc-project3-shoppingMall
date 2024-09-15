package com.example.project3;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("views")
public class ViewController {

    @GetMapping("login")
    public String login() {
        return "login-form";
    }

    @GetMapping("signup")
    public String register() {
        return "signup-form";
    }

    @GetMapping("homepage")
    public String myPage() {
        return "homepage";
    }

    @GetMapping("admin-page")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "admin";
    }






}
