package com.example.finalprj.web.controller;

import com.example.finalprj.db.playground.domain.Playground;
import com.example.finalprj.db.playground.service.PlaygroundService;
import com.example.finalprj.db.user.domain.Authority;
import com.example.finalprj.db.user.domain.User;
import com.example.finalprj.db.user.service.UserService;
import com.example.finalprj.web.controller.vo.UserSignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PasswordEncoder passwordEncoder;
    private final PlaygroundService playgroundService;
    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        List<Playground> pg = playgroundService.getPlaygroundList();
        model.addAttribute("pgList", pg);
        return "signUp";
    }

    @PostMapping("/signup")
    public String signUp(UserSignUpForm form, Model model) {
        final User user = User.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .enabled(true)
                .build();
        userService.findPlaygroundByPgName(form.getPgName()).ifPresent(pg -> user.setPlayground(pg));

        User saved = userService.save(user);
        userService.addAuthority(saved.getId(), Authority.ROLE_MANAGER);
        model.addAttribute("register", "1");
        return "loginForm";
    }

    @GetMapping("/main")
    public String adminPage() {
        return "main";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }
}
