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

    @GetMapping("user-page")
    public String myPage() {
        return "user-page";
    }

    @GetMapping("admin-page")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "admin";
    }


}
