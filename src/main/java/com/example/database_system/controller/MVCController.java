package com.example.database_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MVCController {
    @GetMapping("/login")
    public String ShowLoginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String ShowHomePage() {
        return "home";
    }

    @GetMapping("/create")
    public String ShowCreatePage() {
        return "create";
    }
}
