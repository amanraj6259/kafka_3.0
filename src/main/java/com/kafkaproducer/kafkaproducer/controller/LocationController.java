package com.kafkaproducer.kafkaproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafkaproducer.kafkaproducer.model.DriverLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kafkaproducer.kafkaproducer.service.LocationPublisherService;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationPublisherService service;

    @PostMapping("/update")
    private ResponseEntity<?> updateLocation(@RequestBody DriverLocation location) throws JsonProcessingException {
        service.publishLocation(location);
        return new ResponseEntity<>("message " + "location updated successfully", HttpStatus.OK);

    }
}
