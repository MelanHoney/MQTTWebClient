package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.Sensor;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findByMqttTopic(String topic);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Sensor s set s.isActive = ?2 where s.mqttTopic = ?1")
    void toggleSensorState(String topic, Boolean isActive);

    @Modifying
    @Query("update Sensor s set s.minValue = ?2, s.maxValue = ?3 where s.mqttTopic = ?1")
    void updateMinMaxValue(String topic, Double min, Double max);
}
