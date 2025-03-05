package ru.mirea.kefirproduction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mirea.kefirproduction.dto.DeviceDto;
import ru.mirea.kefirproduction.service.RedisCacheService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DeviceController {

    private final RedisCacheService redisCacheService;

    @GetMapping("/devices")
    public String devices(Model model) {
        List<DeviceDto> devices = redisCacheService.getAllCachedDevices();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(g -> g.getAuthority().equals("ROLE_ADMIN"));
        boolean isUser = auth.getAuthorities().stream()
                .anyMatch(g -> g.getAuthority().equals("ROLE_USER"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUser", isUser);
        model.addAttribute("devices", devices);
        return "devices";
    }
}
