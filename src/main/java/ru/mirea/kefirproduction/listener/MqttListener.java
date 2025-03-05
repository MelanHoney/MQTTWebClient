package ru.mirea.kefirproduction.listener;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.stereotype.Component;
import ru.mirea.kefirproduction.service.MqttService;

@Component
@RequiredArgsConstructor
public class MqttListener {

    private final MqttClient client;
    private final MqttService mqttService;

    @PostConstruct
    public void init() throws MqttException {
        client.setCallback(new MqttCallback() {
            @Override
            public void disconnected(MqttDisconnectResponse disconnectResponse) {
                System.out.println("mqtt listener disconnected: " + disconnectResponse);
            }

            @Override
            public void mqttErrorOccurred(MqttException exception) {
                System.out.println("mqtt listener error occurred: " + exception);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                mqttService.processMessage(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttToken token) {

            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void authPacketArrived(int reasonCode, MqttProperties properties) {

            }
        });
        client.subscribe("pasteurization-line/#", 2);
    }
}
