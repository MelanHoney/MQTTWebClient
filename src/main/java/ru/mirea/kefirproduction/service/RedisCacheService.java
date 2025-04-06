package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.kefirproduction.dao.DeviceStatusDaoImpl;
import ru.mirea.kefirproduction.dto.DeviceDto;
import ru.mirea.kefirproduction.mapper.DeviceMapper;
import ru.mirea.kefirproduction.model.Actuator;
import ru.mirea.kefirproduction.model.Sensor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisCacheService {
    private final DeviceStatusDaoImpl deviceStatusDao;
    private final SensorsService sensorsService;
    private final ActuatorService actuatorService;
    private final DeviceMapper deviceMapper;

    public List<DeviceDto> getAllCachedDevices() {
        List<DeviceDto> sensors = deviceStatusDao.getAllSensors().stream().map(deviceMapper::map).toList();
        List<DeviceDto> actuators = deviceStatusDao.getAllActuators().stream().map(deviceMapper::map).toList();
        List<DeviceDto> devices = new ArrayList<>();
        devices.addAll(sensors);
        devices.addAll(actuators);
        devices.sort(Comparator.comparing(DeviceDto::getMachineName));
        return devices;
    }

    public void saveDeviceStatus(Sensor sensor) {
        deviceStatusDao.saveDeviceStatus(sensor);
    }

    public void saveDeviceStatus(Actuator actuator) {
        deviceStatusDao.saveDeviceStatus(actuator);
    }

    public Object findDevice(String topic) {
        return deviceStatusDao.findDeviceByTopic(topic);
    }

    public Boolean getDeviceStatus(String topic) {
        var device = deviceStatusDao.getDeviceStatus(topic);
        if (device == null) {
            if (topic.contains("sensors")) {
                Sensor sensor = sensorsService.findByTopic(topic).get();
                saveDeviceStatus(sensor);
                return sensor.getIsActive();
            } else {
                Actuator actuator = actuatorService.findByTopic(topic).get();
                saveDeviceStatus(actuator);
                return actuator.getIsActive();
            }
        }
        return device;
    }

    public void toggleDeviceStatus(String topic) {
        if (topic.contains("sensors")) {
            Sensor sensor = sensorsService.toggleSensorStatus(topic);
            saveDeviceStatus(sensor);
        } else {
            Actuator actuator = actuatorService.toggleActuatorStatus(topic);
            saveDeviceStatus(actuator);
        }
    }
}
