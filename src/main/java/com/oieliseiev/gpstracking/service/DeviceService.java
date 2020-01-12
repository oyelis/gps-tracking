package com.oieliseiev.gpstracking.service;

import com.oieliseiev.gpstracking.model.GpsDevice;
import com.oieliseiev.gpstracking.repository.impl.JdbcGpsDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private static final String IMEI_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";

    @Autowired
    private JdbcGpsDeviceRepository gpsDeviceRepository;

    public List<GpsDevice> getUserDevices(Integer userId, Long chatId) {
        return gpsDeviceRepository.getUserDevices(userId);
    }

    public boolean saveDevice(String imei, Integer userId) {
        if (imei.matches(IMEI_REGEX)) {
            return !gpsDeviceRepository.deviceExists(imei) && gpsDeviceRepository.save(imei, userId);
        }
        throw new IllegalArgumentException(imei + " entered 'imei' is wrong. Example: '6a2f41a3-c54c-fce8-32d2-0324e1c32e22'.");
    }
}
