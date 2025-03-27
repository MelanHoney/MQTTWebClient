package ru.mirea.kefirproduction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.kefirproduction.dto.SensorMinMaxValueDto;
import ru.mirea.kefirproduction.model.Sensor;
import ru.mirea.kefirproduction.service.RedisCacheService;
import ru.mirea.kefirproduction.service.SensorsService;

@RestController
@RequiredArgsConstructor
public class SchemeApiController {

    private final RedisCacheService redisCacheService;
    private final SensorsService sensorsService;

    @PatchMapping("/api/sensors")
    public ResponseEntity<?> changeMinMaxValue(@RequestBody SensorMinMaxValueDto dto) {
        Sensor sensor = (Sensor) redisCacheService.findDevice(dto.getTopic());
        sensor.setMinValue(dto.getMinValue());
        sensor.setMaxValue(dto.getMaxValue());
        redisCacheService.saveDeviceStatus(sensor);
        sensorsService.updateMinMaxValue(dto.getTopic(), dto, sensor);
        return ResponseEntity.ok().build();
    }
}
