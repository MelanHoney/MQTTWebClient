package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.ActuatorState;

import java.util.List;

@Repository
public interface ActuatorStateRepository extends JpaRepository<ActuatorState, Long> {
    @Query(value = "SELECT * FROM actuator_states WHERE actuator_id = ?1 ORDER BY timestamp DESC LIMIT 50",
            nativeQuery = true)
    List<ActuatorState> get50NewestReadings(Long actuatorId);
}
