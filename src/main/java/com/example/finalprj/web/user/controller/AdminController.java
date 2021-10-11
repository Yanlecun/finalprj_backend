package com.example.finalprj.web.user.controller;

import com.example.finalprj.db.domain.Faq;
import com.example.finalprj.db.service.FaqService;
import com.example.finalprj.web.user.controller.vo.FaqForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final FaqService faqService;


    @GetMapping("")
    public String admin(Model model) {
        model.addAttribute("site", "admin");
        return "admin";
    }

    @GetMapping("/faqs")
    public String faqs(Model model) {
        List<Faq> faqs = faqService.findAll();

        model.addAttribute("faqs", faqs);
        model.addAttribute("site", "faqs");
        return "faqs";
    }

    @PostMapping(value = "/faqs", consumes = {"application/x-www-form-urlencoded;charset=UTF-8", MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String faqs(FaqForm faqForm, Model model) {
        Faq faq = Faq.builder()
                .subject(faqForm.getSubject())
                .content(faqForm.getContent())
                .build();
        faqService.save(faq);

        model.addAttribute("site", "faqs");
        model.addAttribute("complete", "완료");
        return "redirect:/admin/faqs";
    }

    @DeleteMapping("/faqs")
    public String faqs(Long id) {
        System.out.println(id);
        faqService.deleteById(id);
        return "redirect:/admin/faqs";
    }

    @PutMapping(value="/faqs")
    public String faqs(Faq faq) {
        faqService.updateFaq(faq);
        return "redirect:/admin/faqs";
    }

    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("site", "accounts");
        return "accounts";
    }
}
