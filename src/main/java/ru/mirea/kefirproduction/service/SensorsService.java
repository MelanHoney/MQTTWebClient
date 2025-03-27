package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.kefirproduction.dto.DeviceDto;
import ru.mirea.kefirproduction.dto.SensorMinMaxValueDto;
import ru.mirea.kefirproduction.mapper.DeviceMapper;
import ru.mirea.kefirproduction.model.Machine;
import ru.mirea.kefirproduction.model.Sensor;
import ru.mirea.kefirproduction.model.SensorType;
import ru.mirea.kefirproduction.repository.SensorRepository;
import ru.mirea.kefirproduction.repository.SensorTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorsService {
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final DeviceMapper deviceMapper;

    @Transactional(readOnly = true)
    public Optional<Sensor> findByTopic(String topic) {
        return sensorRepository.findByMqttTopic(topic);
    }

    @Transactional(readOnly = true)
    public List<DeviceDto> findAll() {
        return sensorRepository.findAll().stream()
                .map(deviceMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Sensor save(String typeName, Machine machine, String mqttTopic) {
        SensorType type = sensorTypeRepository.findByName(typeName).orElse(null);
        if (type == null) {
            type = SensorType.builder()
                    .name(typeName)
                    .build();
            sensorTypeRepository.save(type);
        }
        Sensor sensor = Sensor.builder()
                .mqttTopic(mqttTopic)
                .sensorType(type)
                .machine(machine)
                .isActive(true)
                .build();
        return sensorRepository.save(sensor);
    }

    @Transactional
    public Sensor toggleSensorStatus(String topic) {
        var sensor = sensorRepository.findByMqttTopic(topic);
        sensor.get().setIsActive(!sensor.get().getIsActive());
        sensorRepository.save(sensor.get());
        return sensor.orElse(null);
    }

    @Transactional
    public boolean updateMinMaxValue(String topic, SensorMinMaxValueDto dto, Sensor sensor) {
        if (dto.getMinValue() == null) {
            dto.setMinValue(sensor.getMinValue());
        }
        if (dto.getMaxValue() == null) {
            dto.setMaxValue(sensor.getMaxValue());
        }
        sensorRepository.updateMinMaxValue(topic, dto.getMinValue(), dto.getMaxValue());
        return true;
    }
}
