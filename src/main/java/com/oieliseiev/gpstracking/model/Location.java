package com.oieliseiev.gpstracking.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Location {
    private Integer id;
    private Float latitude;
    private Float longitude;
    private LocalDateTime date;
    private Integer gpsDeviceId;
}
