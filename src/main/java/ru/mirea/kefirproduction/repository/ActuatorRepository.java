package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.Actuator;

import java.util.Optional;

@Repository
public interface ActuatorRepository extends JpaRepository<Actuator, Long> {
    Optional<Actuator> findByMqttTopic(String mqttTopic);
}
