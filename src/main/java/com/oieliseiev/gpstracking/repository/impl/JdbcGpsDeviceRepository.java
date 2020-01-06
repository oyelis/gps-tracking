package com.oieliseiev.gpstracking.repository.impl;

import com.oieliseiev.gpstracking.model.GpsDevice;
import com.oieliseiev.gpstracking.repository.GpsDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGpsDeviceRepository implements GpsDeviceRepository {

    private static final String SELECT_DEVICES = "SELECT * FROM tbl_gpsdevice WHERE userId = :userId";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<GpsDevice> getUserDevices(Integer userId, Long chatId) {
        return namedParameterJdbcTemplate.queryForList(SELECT_DEVICES, new MapSqlParameterSource("userId", userId), GpsDevice.class);
    }
}
