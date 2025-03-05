package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.mirea.kefirproduction.model.Actuator;
import ru.mirea.kefirproduction.model.Machine;
import ru.mirea.kefirproduction.model.Sensor;

@Service
@RequiredArgsConstructor
public class MqttService {
    private final MachineService machineService;
    private final SensorsService sensorsService;
    private final ActuatorService actuatorService;
    private final SensorReadingService sensorReadingService;
    private final ActuatorStateService actuatorStateService;
    private final WebSocketService webSocketService;
    private final RedisCacheService redisCacheService;

    @Async
    public void processMessage(String topic, MqttMessage message) {
        String[] parts = topic.split("/");

        if (!isDeviceRegistered(topic, parts[2])) {
            registerNewDevice(parts, topic);
        }

        if (parts[2].equals("sensors")) {
            Sensor sensor = (Sensor) redisCacheService.findDevice(topic);
            sensorReadingService.save(sensor, Double.parseDouble(message.toString()));
        } else {
            Actuator actuator = (Actuator) redisCacheService.findDevice(topic);
            actuatorStateService.save(actuator, Boolean.parseBoolean(message.toString()));
        }

        if (redisCacheService.getDeviceStatus(topic)) {
            webSocketService.sendDeviceData(topic, message.toString());
        } else {
            webSocketService.sendDeviceData(topic, "OFFLINE");
        }
    }

    private boolean isDeviceRegistered(String topic, String deviceType) {
        if (redisCacheService.findDevice(topic) != null) {
            return true;
        }
        if (deviceType.equals("sensors")) {
            return sensorsService.findByTopic(topic).map(sensor -> {
                redisCacheService.saveDeviceStatus(sensor);
                return true;
            }).orElse(false);
        } else {
            return actuatorService.findByTopic(topic).map(actuator -> {
                redisCacheService.saveDeviceStatus(actuator);
                return true;
            }).orElse(false);
        }
    }

    private void registerNewDevice(String[] parts, String topic) {
        Machine machine = machineService.findMachine(parts[1]);
        if (machine == null) {
            machine = machineService.save(parts[1]);
        }
        if (parts[2].equals("sensors")) {
            Sensor sensor = sensorsService.save(parts[3].toUpperCase(), machine, topic);
            redisCacheService.saveDeviceStatus(sensor);
        } else {
            Actuator actuator = actuatorService.save(parts[3].toUpperCase(), machine, topic);
            redisCacheService.saveDeviceStatus(actuator);
        }
    }
}
