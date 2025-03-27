package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final RedisCacheService redisCacheService;
    private final SensorsService sensorsService;
    private final ActuatorService actuatorService;

}
