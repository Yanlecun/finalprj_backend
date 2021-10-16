package com.example.finalprj.web.user.controller;

import com.example.finalprj.db.domain.Entry;
import com.example.finalprj.db.domain.Playground;
import com.example.finalprj.db.domain.User;
import com.example.finalprj.db.service.EntryService;
import com.example.finalprj.db.service.PlaygroundService;
import com.example.finalprj.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService ;
    private final EntryService entryService;
    private final PlaygroundService playgroundService;

    @GetMapping("/reservation")
    public String reservation(@AuthenticationPrincipal User user) {
        return "/user/reservation";
    }

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal User temp, Model model) {
        List<Entry> entries = entryService.findAllByUserIdAndStatusEqual(temp.getId(), 3);
        List<Playground> playgrounds = new ArrayList<>();
        entries.forEach(e -> {
            long id = e.getPlaygroundId();
            playgrounds.add(playgroundService.findById(id).get());
        });

        System.out.println(">>>" + entries);

        model.addAttribute("entries", entries);
        model.addAttribute("playgrounds", playgrounds);

        return "/user/list";
    }
}
