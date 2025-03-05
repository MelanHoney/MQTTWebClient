package ru.mirea.kefirproduction.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.Actuator;
import ru.mirea.kefirproduction.model.Sensor;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class DeviceStatusDaoImpl implements DeviceStatusDao {
    private final RedisTemplate<String, Object> redisTemplate;

    public List<Sensor> getAllSensors() {
        String pattern = "pasteurization-line/*/sensors/*";
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys == null || keys.isEmpty()) {
            return List.of();
        }
        return redisTemplate.opsForValue().multiGet(keys).stream()
                .map(o -> (Sensor) o)
                .toList();
    }

    public List<Actuator> getAllActuators() {
        String pattern = "pasteurization-line/*/actuators/*";
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys == null || keys.isEmpty()) {
            return List.of();
        }
        return redisTemplate.opsForValue().multiGet(keys).stream()
                .map(o -> (Actuator) o)
                .toList();
    }

    @Override
    public void saveDeviceStatus(Sensor sensor) {
        redisTemplate.opsForValue().set(sensor.getMqttTopic(), sensor);
    }

    @Override
    public void saveDeviceStatus(Actuator actuator) {
        redisTemplate.opsForValue().set(actuator.getMqttTopic(), actuator);
    }

    @Override
    public Boolean getDeviceStatus(String topic) {
        Object device = redisTemplate.opsForValue().get(topic);
        if (device == null) {
            return null;
        }
        return (device.getClass() == Sensor.class) ?
                ((Sensor) device).getIsActive() : ((Actuator) device).getIsActive();
    }

    @Override
    public Object findDeviceByTopic(String topic) {
        return redisTemplate.opsForValue().get(topic);
    }
}
