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
    private static final String INSERT_DEVICE = "INSERT INTO tbl_gpsdevice(imei, userId) VALUES (:imei, :userId)";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<GpsDevice> getUserDevices(Integer userId, Long chatId) {
        return namedParameterJdbcTemplate.query(SELECT_DEVICES, new MapSqlParameterSource("userId", userId),
                (rs, i) -> GpsDevice.builder()
                        .id(rs.getInt("id"))
                        .imei(rs.getString("imei"))
                        .isActive(rs.getBoolean("isActive"))
                        .userId(rs.getInt("userId"))
                        .build());
    }

    @Override
    public boolean save(String imei, Integer userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("imei", imei);
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.update(INSERT_DEVICE, mapSqlParameterSource) > 0;
    }
}
