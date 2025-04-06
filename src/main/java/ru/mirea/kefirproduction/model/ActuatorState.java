package ru.mirea.kefirproduction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "actuator_states")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActuatorState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actuator_id", nullable = false)
    private Actuator actuator;

    @Column(nullable = false)
    private Boolean state;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
