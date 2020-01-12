package com.oieliseiev.gpstracking.controller;

import com.oieliseiev.gpstracking.model.Location;
import com.oieliseiev.gpstracking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/telegram")
public class TelegramBotController {

    private static final Logger LOG = Logger.getLogger(TelegramBotController.class.getName());

    @Autowired
    private LocationService locationService;

    @PostMapping
    public void sendLocation(Location location) {
        final boolean success = locationService.addLocation(location);
        LOG.info("Location was added: " + success);
    }

}
