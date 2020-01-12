package com.oieliseiev.gpstracking.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GpsDevice {
    private Integer id;
    private String imei;
    private Integer userId;
    private boolean isActive;
}
