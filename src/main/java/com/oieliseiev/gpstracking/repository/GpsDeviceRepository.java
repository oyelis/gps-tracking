package com.oieliseiev.gpstracking.repository;

import com.oieliseiev.gpstracking.model.GpsDevice;

import java.util.List;

public interface GpsDeviceRepository {

    List<GpsDevice> getUserDevices(Integer userId);

    boolean save(String imei, Integer userId);

    boolean deviceExists(String imei);
}
