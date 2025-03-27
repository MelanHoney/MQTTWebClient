package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.kefirproduction.dto.DeviceReadingDto;
import ru.mirea.kefirproduction.mapper.ReadingsMapper;
import ru.mirea.kefirproduction.model.Actuator;
import ru.mirea.kefirproduction.model.Sensor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingsService {

    private final SensorReadingService sensorReadingService;
    private final ActuatorStateService actuatorStateService;
    private final RedisCacheService redisCacheService;

    public List<DeviceReadingDto> getReadings(String topic) {
        var device = redisCacheService.findDevice(topic);
        if (device.getClass().equals(Sensor.class)) {
            Long id = ((Sensor) device).getId();
            return sensorReadingService.getLast50Readings(id).stream().map(ReadingsMapper::map).toList();
        } else {
            Long id = ((Actuator) device).getId();
            return actuatorStateService.getLast50Readings(id).stream().map(ReadingsMapper::map).toList();
        }
    }

}
