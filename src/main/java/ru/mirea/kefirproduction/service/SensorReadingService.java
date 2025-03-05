package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.kefirproduction.dto.DeviceDto;
import ru.mirea.kefirproduction.model.Sensor;
import ru.mirea.kefirproduction.model.SensorReading;
import ru.mirea.kefirproduction.repository.SensorReadingRepository;
import ru.mirea.kefirproduction.repository.SensorRepository;

@Service
@RequiredArgsConstructor
public class SensorReadingService {
    private final SensorReadingRepository sensorReadingRepository;
    private final SensorRepository sensorRepository;

    @Transactional
    public SensorReading save(Sensor sensor, Double value) {
        SensorReading sensorReading = SensorReading.builder()
                .sensor(sensor)
                .value(value)
                .build();
        return sensorReadingRepository.save(sensorReading);
    }

    @Transactional
    public SensorReading save(DeviceDto device, Double value) {
        Sensor sensor = sensorRepository.findByMqttTopic(device.getTopic()).get();
        SensorReading sensorReading = SensorReading.builder()
                .sensor(sensor)
                .value(value)
                .build();
        return sensorReadingRepository.save(sensorReading);
    }
}
