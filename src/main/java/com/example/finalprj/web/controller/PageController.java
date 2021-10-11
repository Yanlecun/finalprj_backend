package com.example.finalprj.web.controller;


import com.example.finalprj.db.domain.Playground;
import com.example.finalprj.db.service.PlaygroundService;
import com.example.finalprj.db.domain.Authority;
import com.example.finalprj.db.domain.User;
import com.example.finalprj.db.service.UserService;
import com.example.finalprj.web.controller.vo.UserSignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PasswordEncoder passwordEncoder;
    private final PlaygroundService playgroundService;
    private final UserService userService;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User user, @RequestParam(value="error", defaultValue = "false") Boolean error, HttpServletRequest request, Model model) {
        if(user!=null && user.isEnabled()){
            if(user.getAuthorities().contains(Authority.ADMIN_AUTHORITY)){
                return "redirect:/admin";
            }else if(user.getAuthorities().contains(Authority.MANAGER_AUTHORITY)){
                return "redirect:/manager";
            }
        }
        SavedRequest savedRequest = requestCache.getRequest(request, null);
        model.addAttribute("error", error);
        return "loginForm";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        List<Playground> pg = playgroundService.getPlaygroundList();
        model.addAttribute("pgList", pg);
        return "signUp";
    }

    @PostMapping(value = "/signup", consumes = {"application/x-www-form-urlencoded;charset=UTF-8", MediaType.APPLICATION_FORM_URLENCODED_VALUE})
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
        model.addAttribute("register", true);
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
