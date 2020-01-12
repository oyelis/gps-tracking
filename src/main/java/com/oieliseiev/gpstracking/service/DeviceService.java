package com.oieliseiev.gpstracking.service;

import com.oieliseiev.gpstracking.model.GpsDevice;
import com.oieliseiev.gpstracking.repository.impl.JdbcGpsDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private JdbcGpsDeviceRepository gpsDeviceRepository;

    public List<GpsDevice> getUserDevices(Integer userId, Long chatId) {
        return gpsDeviceRepository.getUserDevices(userId, chatId);
    }

    public boolean saveDevice(String imei, Integer userId) {
        return gpsDeviceRepository.save(imei, userId);
    }
}
