package ru.mirea.kefirproduction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ru.mirea.kefirproduction.dto.SensorMinMaxValueDto;
import ru.mirea.kefirproduction.model.Sensor;
import ru.mirea.kefirproduction.model.enums.UserAction;
import ru.mirea.kefirproduction.service.RedisCacheService;
import ru.mirea.kefirproduction.service.SensorsService;
import ru.mirea.kefirproduction.service.UserLogService;

@RestController
@RequiredArgsConstructor
public class SchemeApiController {

    private final RedisCacheService redisCacheService;
    private final SensorsService sensorsService;
    private final UserLogService userLogService;

    @PatchMapping("/api/sensors")
    public ResponseEntity<?> changeMinMaxValue(@RequestBody SensorMinMaxValueDto dto) {
        Sensor sensor = (Sensor) redisCacheService.findDevice(dto.getTopic());
        sensor.setMinValue(dto.getMinValue());
        sensor.setMaxValue(dto.getMaxValue());
        redisCacheService.saveDeviceStatus(sensor);
        sensorsService.updateMinMaxValue(dto.getTopic(), dto, sensor);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userLogService.logUserAction(UserAction.CHANGE_MIN_MAX_VALUE + " " +
                dto.getTopic(), user.getUsername());
        return ResponseEntity.ok().build();
    }
}
