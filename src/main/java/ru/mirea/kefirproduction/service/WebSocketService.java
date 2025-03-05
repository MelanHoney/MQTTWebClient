package ru.mirea.kefirproduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.mirea.kefirproduction.dto.DeviceDataDto;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    @Async
    public void sendDeviceData(String topic, String value) {
        messagingTemplate.convertAndSend("/topic/data",
                DeviceDataDto.builder()
                        .mqttTopic(topic)
                        .value(value)
                        .build());
    }
}
