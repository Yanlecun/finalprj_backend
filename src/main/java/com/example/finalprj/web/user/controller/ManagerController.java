package com.example.finalprj.web.user.controller;

import com.example.finalprj.db.domain.User;
import com.example.finalprj.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final UserService userService;

    @GetMapping("/usage")
    public String faqs(Model model) {

        model.addAttribute("site", "usage");
        model.addAttribute("url", "manager");
        model.addAttribute("id", 1);
        return "usage";
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

    @GetMapping("/reservation")
    public String accounts(Model model) {
        List<User> managers = userService.findAllManager();

        model.addAttribute("managers", managers);
        model.addAttribute("site", "reservation");
        model.addAttribute("url", "manager");
        model.addAttribute("id", 1);
        return "reservation";
    }

//    @DeleteMapping("/accounts")
//    public String accounts(Long id) {
//        User user = userService.findById(id).get();
//        Playground pg = playgroundRepository.findById(user.getPlayground().getId()).get();
//        pg.setUser(null);
//        playgroundRepository.save(pg);
//
//        userService.deleteById(id);
//        return "redirect:/admin/accounts";
//    }

    @GetMapping("/list")
    public String list(Model model) {

        model.addAttribute("site", "list");
        model.addAttribute("url", "manager");
        model.addAttribute("id", 1);
        return "list";
    }
}
