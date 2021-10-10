package com.example.finalprj.web.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String admin() {
        return "admin";
    }

    @GetMapping("/faqs")
    public String faqs() {
        return "faqs";
    }

    @GetMapping("/accounts")
    public String accounts() {
        return "accounts";
    }
}
