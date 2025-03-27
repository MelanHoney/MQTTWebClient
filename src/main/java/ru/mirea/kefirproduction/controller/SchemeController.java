package ru.mirea.kefirproduction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SchemeController {

    @GetMapping("/scheme")
    public String scheme(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(g -> g.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        return "scheme";
    }
}
