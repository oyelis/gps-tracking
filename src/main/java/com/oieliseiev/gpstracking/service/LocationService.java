package com.oieliseiev.gpstracking.service;

import com.oieliseiev.gpstracking.model.Location;
import com.oieliseiev.gpstracking.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public boolean addLocation(Location location) {
        return locationRepository.addLocation(location);
    }
}
