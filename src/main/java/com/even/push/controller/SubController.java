package com.even.push.controller;

import com.even.push.dto.SubMsgDto;
import com.even.push.utils.JsonUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther yidadi
 * @Date 17-9-4 下午3:42
 */
@RestController
@RequestMapping("/api/push-server/")
public class SubController {
    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     *
     * @param subMsgDto
     */
    @RequestMapping(value = "sub",method = RequestMethod.POST)
    public void sub(SubMsgDto subMsgDto){
        ProducerRecord producerRecord = new ProducerRecord(subMsgDto.getMqtopic(), JsonUtils.toJson(subMsgDto));
        kafkaProducer.send(producerRecord);
    }
}
