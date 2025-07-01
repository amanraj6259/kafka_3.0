package com.kafkaproducer.kafkaproducer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverLocation {

    private String driverId;

    private double latitude;

    private double longitude;

    private String timestamp;
}
