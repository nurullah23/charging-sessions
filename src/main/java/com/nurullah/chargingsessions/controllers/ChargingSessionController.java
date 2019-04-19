package com.nurullah.chargingsessions.controllers;

import com.nurullah.chargingsessions.models.ChargingSessionDto;
import com.nurullah.chargingsessions.models.ChargingSummary;
import com.nurullah.chargingsessions.services.ChargingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@RestController
public class ChargingSessionController {

    private ChargingSessionService chargingSessionService;

    @Autowired
    public ChargingSessionController(ChargingSessionService chargingSessionService) {
        this.chargingSessionService = chargingSessionService;
    }

    @PostMapping(value = "/chargingSession")
    public void submitChargingSession(@RequestBody ChargingSessionDto chargingSession) {
        this.chargingSessionService.createChargingSession(chargingSession);
    }

    @PutMapping(value = "/chargingSession/{sessionId}")
    public void stopChargingSession(@PathVariable("sessionId") long id) {
        this.chargingSessionService.stopChargingSession(id);
    }

    @GetMapping(value = "/chargingSessions")
    public ChargingSummary getChargingSummary() {
        return this.chargingSessionService.getChargingSummary();
    }

    @RequestMapping(value = "/chargingSessions/started", method = RequestMethod.GET)
    public void getStartedChargingSummary() {
        // TODO Could
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/chargingSessions/stopped", method = RequestMethod.GET)
    public void getStoppedChargingSummary() {
        // TODO Could
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/chargingSessions", method = RequestMethod.DELETE)
    public void deleteChargingSessions() {
        // TODO Could
        throw new NotImplementedException();
    }

}
