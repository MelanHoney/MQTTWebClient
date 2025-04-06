package ru.mirea.kefirproduction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_readings", indexes = {
        @Index(name = "idx_sensorId_timestamp", columnList = "sensor_id, timestamp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @Column(nullable = false)
    private Double value;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
