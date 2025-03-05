package ru.mirea.kefirproduction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "actuators")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Actuator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private ActuatorType actuatorType;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "mqtt_topic", nullable = false, unique = true)
    private String mqttTopic;
}
