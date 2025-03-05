package ru.mirea.kefirproduction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.kefirproduction.service.RedisCacheService;

@RestController
@RequiredArgsConstructor
public class DeviceApiController {

    private final RedisCacheService redisCacheService;

    @PostMapping("api/devices/toggle")
    public ResponseEntity<Void> toggleDevice(@RequestParam String topic) {
        redisCacheService.toggleDeviceStatus(topic);
        return ResponseEntity.ok().build();
    }
}
