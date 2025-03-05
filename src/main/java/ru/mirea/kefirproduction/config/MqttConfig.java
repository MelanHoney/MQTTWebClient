package ru.mirea.kefirproduction.config;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    @Value("${mqtt.client.id}")
    private String CLIENT_ID;
    @Value("${mqtt.broker.url}")
    private String BROKER_URL;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(BROKER_URL, CLIENT_ID);
        mqttClient.connect();
        return mqttClient;
    }
}
