package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.mirea.kefirproduction.dto.DeviceDataDto;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    @Async
    public void sendDeviceData(String topic, String value, Double minValue, Double maxValue) {
        messagingTemplate.convertAndSend("/topic/data",
                DeviceDataDto.builder()
                        .mqttTopic(topic)
                        .value(value)
                        .minValue(minValue)
                        .maxValue(maxValue)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
