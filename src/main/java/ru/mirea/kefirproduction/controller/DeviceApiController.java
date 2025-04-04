package ru.mirea.kefirproduction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ru.mirea.kefirproduction.dto.DeviceReadingDto;
import ru.mirea.kefirproduction.model.enums.UserAction;
import ru.mirea.kefirproduction.service.ReadingsService;
import ru.mirea.kefirproduction.service.RedisCacheService;
import ru.mirea.kefirproduction.service.UserLogService;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceApiController {

    private final RedisCacheService redisCacheService;
    private final ReadingsService readingsService;
    private final UserLogService userLogService;

    @PostMapping("/toggle")
    public ResponseEntity<Void> toggleDevice(@RequestParam String topic) {
        redisCacheService.toggleDeviceStatus(topic);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userLogService.logUserAction(UserAction.CHANGE_STATE + " " + topic, user.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<DeviceReadingDto>> getHistory(@RequestParam String topic) {

        List<DeviceReadingDto> readings = readingsService.getReadings(topic);

        return ResponseEntity.ok().body(readings);
    }
}
