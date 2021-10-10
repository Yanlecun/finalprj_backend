package com.example.finalprj.web.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String admin(Model model) {
        model.addAttribute("site", "admin");
        return "admin";
    }

    @GetMapping("/faqs")
    public String faqs(Model model) {
        model.addAttribute("site", "faqs");
        return "faqs";
    }

    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("site", "accounts");
        return "accounts";
    }
}
