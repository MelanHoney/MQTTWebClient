package ru.mirea.kefirproduction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorMinMaxValueDto {
    private String topic;
    private Double minValue;
    private Double maxValue;
}
