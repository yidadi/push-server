package com.even.push.mqtt;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther yidadi
 * @Date 17-9-1 下午4:52
 */

public class MattConsumer {
    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private KafkaProducer kafkaProducer;

    private static final String TOPIC = "com/even/sub";
    private static final Integer QOS = 1;


    public void sub() throws MqttException {
        mqttClient.subscribe(TOPIC,QOS);
        mqttClient.setCallback(new MqttCallBack(kafkaProducer));
    }
}
