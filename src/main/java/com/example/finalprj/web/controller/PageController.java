package com.example.finalprj.web.controller;


import com.example.finalprj.db.domain.Authority;
import com.example.finalprj.db.domain.Entry;
import com.example.finalprj.db.domain.Playground;
import com.example.finalprj.db.domain.User;
import com.example.finalprj.db.repository.PlaygroundRepository;
import com.example.finalprj.db.service.EntryService;
import com.example.finalprj.db.service.PlaygroundService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PasswordEncoder passwordEncoder;
    private final PlaygroundService playgroundService;
    private final UserService userService;
    private final PlaygroundRepository playgroundRepository;
    private final EntryService entryService;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User user, @RequestParam(value = "error", defaultValue = "false") Boolean error, HttpServletRequest request, Model model) {
        if (user != null && user.isEnabled()) {
            if (user.getAuthorities().contains(Authority.ADMIN_AUTHORITY)) {
                return "redirect:/admin";
            } else if (user.getAuthorities().contains(Authority.MANAGER_AUTHORITY)) {
                Long id =  user.getPlayground().getId();
                return String.format("redirect:/main/") + id; // 각각 관리하는 페이지로 이동
            } else {
                return "redirect:/main";
            }
        }
        SavedRequest savedRequest = requestCache.getRequest(request, null);

        model.addAttribute("error", error);
        return "loginForm";
    }

    @GetMapping("/signup")
    public String signUp(@RequestParam String site, Model model) {
        if(site.equals("manager")) {
            List<Playground> pg = playgroundService.getPlaygroundList();
            model.addAttribute("pgList", pg);
        }
        model.addAttribute("site", site);
        return "signUp";
    }

    @PostMapping(value = "/signup", consumes = {"application/x-www-form-urlencoded;charset=UTF-8", MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String signUp(@RequestParam String site, UserSignUpForm form, Model model) {
        final User user = User.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .enabled(true)
                .build();
        if(site.equals("manager")) {
            userService.findPlaygroundByPgName(form.getPgName()).ifPresent(pg -> {
                user.setPlayground(pg);
                User saved = userService.save(user);

                Playground playground = playgroundRepository.findById(saved.getPlayground().getId()).get();
                playground.setUser(saved);
                playgroundRepository.save(playground);

                userService.addAuthority(saved.getId(), Authority.ROLE_MANAGER);
            });
        } else {
            user.setDogNum(form.getDogNum());
            User saved = userService.save(user);

            userService.addAuthority(saved.getId(), Authority.ROLE_USER);
        }

        model.addAttribute("register", true);
        return "loginForm";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("site", "main");
        model.addAttribute("url", "main");

        return "main";
    }

    @GetMapping("/main/{id}")
    public String mainPage(@PathVariable(name="id") long playgroundId, Model model) {
        Playground playground = playgroundService.findById(playgroundId).get() ;
        List<Entry> entries = entryService.findAllByPlaygroundIdAndStatusEqual(playgroundId, 2);

        model.addAttribute("userNum", entries.size());
        model.addAttribute("pg", playground);
        model.addAttribute("site", "main");
        model.addAttribute("url", "manager");
        model.addAttribute("id", playgroundId);
        return "main";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }
}
