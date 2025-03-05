package ru.mirea.kefirproduction.mapper;

import org.springframework.stereotype.Component;
import ru.mirea.kefirproduction.dto.DeviceDto;
import ru.mirea.kefirproduction.model.Actuator;
import ru.mirea.kefirproduction.model.Sensor;

@Component
public class DeviceMapper {
    public DeviceDto map(Sensor sensor) {
        return DeviceDto.builder()
                .topic(sensor.getMqttTopic())
                .machineName(sensor.getMachine().getName())
                .deviceType("SENSOR")
                .deviceModel(sensor.getSensorType().getName())
                .isActive(sensor.getIsActive())
                .build();
    }

    public DeviceDto map(Actuator actuator) {
        return DeviceDto.builder()
                .topic(actuator.getMqttTopic())
                .machineName(actuator.getMachine().getName())
                .deviceType("ACTUATOR")
                .deviceModel(actuator.getActuatorType().getName())
                .isActive(actuator.getIsActive())
                .build();
    }
}
