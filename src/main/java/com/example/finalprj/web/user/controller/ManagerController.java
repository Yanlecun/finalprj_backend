package com.example.finalprj.web.user.controller;

import com.example.finalprj.db.domain.User;
import com.example.finalprj.db.service.EntryService;
import com.example.finalprj.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final UserService userService;
    private final EntryService entryService;

    @GetMapping("/usage/{id}")
    public String usage(@PathVariable(name = "id") long playgroundId, Model model) {
        List<Long> userIds = entryService.findAllById(playgroundId, 2); // 이용중인 사람들 목록
        List<User> users = new ArrayList<>();
        for (Long id : userIds) {
            users.add(userService.findById(id).orElse(null));
        }

        model.addAttribute("users", users);
        model.addAttribute("site", "usage");
        model.addAttribute("url", "manager");
        model.addAttribute("id", playgroundId);
        return "usage";
    }

    @PutMapping("/usage/{id}")
    public String usage(@PathVariable(name = "id") long playgroundId, long userId) {
        entryService.exitUser(userId, playgroundId);

        return "redirect:/manager/usage/" + playgroundId;
    }

//    @PostMapping(value = "/faqs", consumes = {"application/x-www-form-urlencoded;charset=UTF-8", MediaType.APPLICATION_FORM_URLENCODED_VALUE})
//    public String faqs(FaqForm faqForm, Model model) {
//        Faq faq = Faq.builder()
//                .subject(faqForm.getSubject())
//                .content(faqForm.getContent())
//                .build();
//        faqService.save(faq);
//
//        model.addAttribute("site", "faqs");
//        model.addAttribute("complete", "완료");
//        model.addAttribute("url", "admin");
//        return "redirect:/admin/faqs";
//    }
//
//    @DeleteMapping("/faqs")
//    public String faqs(Long id) {
//        faqService.deleteById(id);
//        return "redirect:/admin/faqs";
//    }
//
//    @PutMapping(value = "/faqs")
//    public String faqs(Faq faq) {
//        faqService.updateFaq(faq);
//        return "redirect:/admin/faqs";
//    }

    //////////////////////////////////////////////////////////

    @GetMapping("/reservation/{id}")
    public String reservation(@PathVariable(name = "id") long playgroundId, Model model) {
        List<Long> userIds = entryService.findAllById(playgroundId, 1);
        List<User> users = new ArrayList<>();
        for (Long id : userIds) {
            users.add(userService.findById(id).orElse(null));
        }
        model.addAttribute("users", users);
        model.addAttribute("site", "reservation");
        model.addAttribute("url", "manager");
        model.addAttribute("id", playgroundId);

        return "reservation";
    }

    @PutMapping("/reservation/{id}")
    public String reservation1(@PathVariable(name = "id") long playgroundId, long userId) {
        entryService.entryUser(userId, playgroundId);

        return "redirect:/manager/reservation/" + playgroundId;
    }

    @DeleteMapping("/reservation/{id}")
    public String reservation2(@PathVariable(name = "id") long playgroundId, long userId) {
        User user = userService.findById(userId).get();
        System.out.println(user);
        entryService.deleteUserIdStatusEquals(userId, playgroundId, 1); // status가 1인 userid 찾아서 지우기
        if (!user.isEnabled()) {
            // 회원 아닌 사람 지울까 말까
        }
        return "redirect:/manager/reservation/" + playgroundId;
    }

    //////////////////////////////////////////////////////////

    @GetMapping("/list/{id}")
    public String list(@PathVariable(name = "id") long playgroundId, Model model) {

        model.addAttribute("site", "list");
        model.addAttribute("url", "manager");
        model.addAttribute("id", playgroundId);
        return "list";
    }
}
