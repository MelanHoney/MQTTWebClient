package ru.mirea.kefirproduction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mirea.kefirproduction.dto.UserInfoDto;
import ru.mirea.kefirproduction.dto.UserRegistrationDto;
import ru.mirea.kefirproduction.model.enums.UserAction;
import ru.mirea.kefirproduction.service.UserLogService;
import ru.mirea.kefirproduction.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserLogService userLogService;

    @GetMapping
    public String adminPage(Model model) {
        List<UserInfoDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute UserRegistrationDto user) {
        userService.registerUser(user);
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userLogService.logUserAction(UserAction.CREATE_USER + " " + user.getEmail(), admin.getUsername());
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userLogService.logUserAction(UserAction.DELETE_USER + " " + id, admin.getUsername());
        return "redirect:/admin";
    }
}
