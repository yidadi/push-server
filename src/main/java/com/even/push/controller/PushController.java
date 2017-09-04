package com.even.push.controller;

import com.even.push.dto.PushMsgDto;
import com.even.push.exception.PushException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Auther yidadi
 * @Date 17-9-1 下午4:31
 */
@RestController
@RequestMapping("/api/push-server/")
public class PushController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MqttClient mqttProducer;

    /**
     *
     * @param pushMsgDto
     */
    @RequestMapping(value = "pushTopic",method = RequestMethod.POST)
    public void pushTopic(PushMsgDto pushMsgDto){
        Set<String> datas = redisTemplate.boundSetOps(pushMsgDto.getTopicPre()).members();
        if(datas.isEmpty()){
            throw new PushException("the register user is empty");
        }else{
            datas.forEach(data->{
                MqttMessage message = new MqttMessage();
                message.setQos(pushMsgDto.getQos());
                message.setRetained(false);
                message.setPayload(pushMsgDto.getContent().getBytes());
                try {
                    mqttProducer.publish(pushMsgDto.getTopicPre().concat(data),message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     *
     * @param pushMsgDto
     */
    @RequestMapping(value = "pushOne",method = RequestMethod.POST)
    public void pushOne(PushMsgDto pushMsgDto){
        MqttMessage message = new MqttMessage();
        message.setQos(pushMsgDto.getQos());
        message.setRetained(false);
        message.setPayload(pushMsgDto.getContent().getBytes());
        try {
            mqttProducer.publish(pushMsgDto.getTopicPre().concat(pushMsgDto.getMark()),message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
