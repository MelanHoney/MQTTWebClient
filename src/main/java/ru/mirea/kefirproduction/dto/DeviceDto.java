package ru.mirea.kefirproduction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private String topic;
    private String machineName;
    private String deviceType;
    private String deviceModel;
    private Boolean isActive;
}
