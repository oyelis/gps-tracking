package com.oieliseiev.gpstracking.repository.impl;

import com.oieliseiev.gpstracking.model.Location;
import com.oieliseiev.gpstracking.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class JdbcLocationRepository implements LocationRepository {

    private static final String INSERT_LOCATION = "INSERT INTO tbl_location(latitude, longitude, date, gpsDeviceId) VALUES (:latitude, :longitude, :date, :gpsDeviceId)";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean addLocation(Location location) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("latitude", location.getLatitude());
        mapSqlParameterSource.addValue("longitude", location.getLongitude());
        mapSqlParameterSource.addValue("date", Timestamp.valueOf(LocalDateTime.now()));
        mapSqlParameterSource.addValue("gpsDeviceId", location.getGpsDeviceId());
        return namedParameterJdbcTemplate.update(INSERT_LOCATION, mapSqlParameterSource) > 0;
    }
}
