package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.kefirproduction.dto.DeviceDto;
import ru.mirea.kefirproduction.model.Actuator;
import ru.mirea.kefirproduction.model.ActuatorState;
import ru.mirea.kefirproduction.repository.ActuatorRepository;
import ru.mirea.kefirproduction.repository.ActuatorStateRepository;

@Service
@RequiredArgsConstructor
public class ActuatorStateService {
    private final ActuatorStateRepository actuatorStateRepository;
    private final ActuatorRepository actuatorRepository;

    @Transactional
    ActuatorState save(Actuator actuator, Boolean value) {
        ActuatorState state = ActuatorState.builder()
                .actuator(actuator)
                .state(value)
                .build();
        return actuatorStateRepository.save(state);
    }

    @Transactional
    ActuatorState save(DeviceDto device, Boolean value) {
        Actuator actuator = actuatorRepository.findByMqttTopic(device.getTopic()).get();
        ActuatorState state = ActuatorState.builder()
                .actuator(actuator)
                .state(value)
                .build();
        return actuatorStateRepository.save(state);
    }
}
