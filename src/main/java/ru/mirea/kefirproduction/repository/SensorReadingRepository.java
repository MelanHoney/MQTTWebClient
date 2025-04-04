package ru.mirea.kefirproduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.kefirproduction.model.SensorReading;

import java.util.List;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
    @Query(value = "SELECT * FROM sensor_readings sr WHERE sensor_id = ?1 ORDER BY timestamp DESC LIMIT 50",
            nativeQuery = true)
    List<SensorReading> get50NewestReadings(Long sensorId);
}
