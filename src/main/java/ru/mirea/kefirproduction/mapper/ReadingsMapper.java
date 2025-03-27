package ru.mirea.kefirproduction.mapper;

import lombok.experimental.UtilityClass;
import ru.mirea.kefirproduction.dto.DeviceReadingDto;
import ru.mirea.kefirproduction.model.ActuatorState;
import ru.mirea.kefirproduction.model.SensorReading;

@UtilityClass
public class ReadingsMapper {

    public DeviceReadingDto map(SensorReading reading) {
        return DeviceReadingDto.builder()
                .timestamp(reading.getTimestamp())
                .value(reading.getValue())
                .build();
    }

    public DeviceReadingDto map(ActuatorState reading) {
        double value;
        if (reading.getState()) {
            value = 1;
        } else {
            value = 0;
        }
        return DeviceReadingDto.builder()
                .timestamp(reading.getTimestamp())
                .value(value)
                .build();
    }
}
