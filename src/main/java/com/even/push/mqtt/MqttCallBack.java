package com.even.push.mqtt;

import com.even.push.dto.SubMsgDto;
import com.even.push.utils.JsonUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther yidadi
 * @Date 17-9-4 下午3:38
 */
public class MqttCallBack implements MqttCallback {
    private KafkaProducer kafkaProducer;

    public MqttCallBack(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void connectionLost(Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        SubMsgDto subMsgDto = (SubMsgDto)JsonUtils.readFromJson(new String(message.getPayload()),SubMsgDto.class);
        kafkaProducer.send(new ProducerRecord(subMsgDto.getMqtopic(),JsonUtils.toJson(subMsgDto)));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}