package com.even.push.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @Auther yidadi
 * @Date 17-9-1 下午4:38
 */
@Configuration
public class MqttConfig {
    private String userName;
    private String passWord;
    public  String host = "tcp://192.168.36.102:1883";

    public String TOPIC = "tokudu/yzq124";
    private static final String clientid ="server";
    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(host, UUID.randomUUID().toString());
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
