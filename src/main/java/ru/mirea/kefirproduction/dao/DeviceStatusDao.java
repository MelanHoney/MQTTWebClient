package ru.mirea.kefirproduction.dao;

import ru.mirea.kefirproduction.model.Actuator;
import ru.mirea.kefirproduction.model.Sensor;

import java.util.List;

public interface DeviceStatusDao {
    List<Sensor> getAllSensors();
    List<Actuator> getAllActuators();
    void saveDeviceStatus(Sensor dto);
    void saveDeviceStatus(Actuator dto);
    Boolean getDeviceStatus(String topic);
    Object findDeviceByTopic(String topic);
}
