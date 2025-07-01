package com.kafkaconsumer.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaconsumer.kafkaconsumer.model.DriverLocation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RiderNotification {
    ObjectMapper obj = new ObjectMapper();

    @KafkaListener(topics="${spring.kafka.topic.driver-location}", groupId = "${spring.kafka.consumer.group-id}")
    public void riderNotificationConsumer(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String key = record.key();
        String value = record.value();
        DriverLocation location = obj.readValue(value, DriverLocation.class);
        System.out.println("Location updated for driver " + location.getDriverId());
        System.out.println("Location updated with longitude and latitide " + location.getLongitude() + " " + location.getLatitude());

    }
}
