package ru.mirea.kefirproduction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "opc_data")
@NoArgsConstructor
@AllArgsConstructor
public class OpcData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "node_id", nullable = false)
    private String nodeId;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
