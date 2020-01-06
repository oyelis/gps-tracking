package com.oieliseiev.gpstracking.model;

import lombok.Data;

@Data
public class GpsDevice {
    private Integer id;
    private String imei;
    private Integer userId;
    private boolean isActive;
}
