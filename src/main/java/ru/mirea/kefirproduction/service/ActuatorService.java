package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.kefirproduction.dto.DeviceDto;
import ru.mirea.kefirproduction.mapper.DeviceMapper;
import ru.mirea.kefirproduction.model.Actuator;
import ru.mirea.kefirproduction.model.ActuatorType;
import ru.mirea.kefirproduction.model.Machine;
import ru.mirea.kefirproduction.repository.ActuatorRepository;
import ru.mirea.kefirproduction.repository.ActuatorTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActuatorService {
    private final ActuatorRepository actuatorRepository;
    private final ActuatorTypeRepository actuatorTypeRepository;
    private final DeviceMapper deviceMapper;

    @Transactional(readOnly = true)
    public Optional<Actuator> findByTopic(String topic) {
        return actuatorRepository.findByMqttTopic(topic);
    }

    @Transactional(readOnly = true)
    public Optional<DeviceDto> findById(Long id) {
        return actuatorRepository.findById(id).map(deviceMapper::map);
    }

    @Transactional(readOnly = true)
    public List<DeviceDto> findAll() {
        return actuatorRepository.findAll().stream()
                .map(deviceMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Actuator save(String typeName, Machine machine, String topic) {
        ActuatorType type = actuatorTypeRepository.findByName(typeName).orElse(null);
        if (type == null) {
            type = ActuatorType.builder()
                    .name(typeName)
                    .build();
            actuatorTypeRepository.save(type);
        }
        Actuator actuator = Actuator.builder()
                .actuatorType(type)
                .machine(machine)
                .mqttTopic(topic)
                .isActive(true)
                .build();
        return actuatorRepository.save(actuator);
    }


    @Transactional
    public Actuator toggleActuatorStatus(String topic) {
        var actuator = actuatorRepository.findByMqttTopic(topic);
        actuator.get().setIsActive(!actuator.get().getIsActive());
        actuatorRepository.save(actuator.get());
        return actuator.orElse(null);
    }
}
