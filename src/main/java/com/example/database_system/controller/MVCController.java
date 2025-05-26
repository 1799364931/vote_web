package com.example.database_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class MVCController {
    @GetMapping
    public String ShowLoginPage() {
        return "login"; // 返回你自己的 login.html
    }


}
