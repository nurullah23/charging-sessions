package com.nurullah.chargingsessions.controllers;

import com.nurullah.chargingsessions.models.ChargingSessionDto;
import com.nurullah.chargingsessions.models.ChargingSummary;
import com.nurullah.chargingsessions.services.ChargingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Charging session controller.
 */
@RestController
public class ChargingSessionController {

    private ChargingSessionService chargingSessionService;

    /**
     * Instantiates a new Charging session controller.
     *
     * @param chargingSessionService the charging session service instance
     */
    @Autowired
    public ChargingSessionController(ChargingSessionService chargingSessionService) {
        this.chargingSessionService = chargingSessionService;
    }

    /**
     * Submit charging session.
     *
     * @param chargingSession the charging session
     */
    @PostMapping(value = "/chargingSession")
    public void submitChargingSession(@RequestBody ChargingSessionDto chargingSession) {
        this.chargingSessionService.createChargingSession(chargingSession);
    }


    /**
     * Stop charging session.
     *
     * @param id charging session id
     */
    @PutMapping(value = "/chargingSession/{sessionId}")
    public void stopChargingSession(@PathVariable("sessionId") long id) {
        this.chargingSessionService.stopChargingSession(id);
    }


    /**
     * Gets charging summary.
     *
     * @return the charging session summary
     */
    @GetMapping(value = "/chargingSessions")
    public ChargingSummary getChargingSummary() {
        return this.chargingSessionService.getChargingSummary();
    }


    /**
     * Gets charging session summary for sessions with status {@link com.nurullah.chargingsessions.enums.ChargingStatus#IN_PROGRESS}.
     */
    @GetMapping(value = "/chargingSessions/started")
    public void getStartedChargingSummary() {
        // TODO Could
        throw new UnsupportedOperationException();
    }


    /**
     * Gets charging session summary for sessions with status {@link com.nurullah.chargingsessions.enums.ChargingStatus#FINISHED}.
     */
    @GetMapping(value = "/chargingSessions/stopped")
    public void getStoppedChargingSummary() {
        // TODO Could
        throw new UnsupportedOperationException();
    }

    /**
     * Delete finished charging sessions.
     */
    @DeleteMapping(value = "/chargingSessions")
    public void deleteChargingSessions() {
        // TODO Could
        throw new UnsupportedOperationException();
    }

}
