package com.even.push.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Auther yidadi
 * @Date 17-9-4 下午3:49
 */
@Configuration
public class KafkaConfig {
    @Value("${com.even.kafka.servers}")
    private String KafkaServers;

    @Bean
    public KafkaProducer<String,String> kafkaProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaServers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }
}
