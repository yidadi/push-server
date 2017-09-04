package com.even.push.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @Auther yidadi
 * @Date 17-9-1 下午4:38
 */
@Configuration
public class MqttConfig {
    @Value("${com.even.mqtt.userName}")
    private String userName;
    @Value("${com.even.mqtt.passWord}")
    private String passWord;
    @Value("${com.even.mqtt.host}")
    private String host;
    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(host, UUID.randomUUID().toString());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        mqttClient.connect(options);
        return mqttClient;
    }


    /**
     *
     * @return
     * @throws MqttException
     */
    @Bean
    public MqttClient mqttProducer() throws MqttException {
        MqttClient mqttProducer = new MqttClient(host, UUID.randomUUID().toString());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        mqttProducer.connect(options);
        return mqttProducer;
    }
}
