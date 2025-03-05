package ru.mirea.kefirproduction.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.mirea.kefirproduction.dto.DeviceDataDto;

@Controller
public class WebSocketController {

    @MessageMapping("")
    @SendTo("/topic/data")
    public DeviceDataDto updateValue(DeviceDataDto data) {
        return data;
    }
}
