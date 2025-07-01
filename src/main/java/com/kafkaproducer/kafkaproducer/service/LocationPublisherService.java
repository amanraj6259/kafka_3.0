package com.kafkaproducer.kafkaproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaproducer.kafkaproducer.model.DriverLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LocationPublisherService {

    @Value("${spring.kafka.topic.driver-location}")
    private  String topic;

    @Autowired
    private  KafkaTemplate<String,String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publishLocation(DriverLocation location) throws JsonProcessingException {
        String key = location.getDriverId();
        String value = objectMapper.writeValueAsString(location);
        kafkaTemplate.send(topic,key,value);
    }


}
