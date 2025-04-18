package ru.mirea.kefirproduction.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sensors")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private SensorType sensorType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "mqtt_topic", nullable = false, unique = true)
    private String mqttTopic;
}
