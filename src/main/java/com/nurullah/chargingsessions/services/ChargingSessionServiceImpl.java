package com.nurullah.chargingsessions.services;

import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSessionDto;
import com.nurullah.chargingsessions.models.ChargingSummary;
import com.nurullah.chargingsessions.repositories.ChargingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ChargingSessionService}
 *
 * Responsible from
 */
@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {

    private ChargingSessionRepository chargingSessionRepository;

    /**
     * Instantiates a new Charging session service.
     *
     * @param chargingSessionRepository the charging session repository
     */
    @Autowired
    public ChargingSessionServiceImpl(ChargingSessionRepository chargingSessionRepository) {
        this.chargingSessionRepository = chargingSessionRepository;
    }


    /**
     * Create charging session instance from {@link ChargingSessionDto}.
     *
     * @param chargingSessionDto the charging session dto
     * @return <code>true</code> if new charging session is added successfully, otherwise <code>falue</code>.
     */
    @Override
    public boolean createChargingSession(ChargingSessionDto chargingSessionDto) {
        ChargingSession chargingSession = ChargingSession.valueOf(chargingSessionDto);
        return chargingSessionRepository.insert(chargingSession);
    }


    /**
     * Stop charging session boolean.
     *
     * @param id charging session id
     * @return <code>true</code> if value is changed, otherwise <code>false</code>
     */
    @Override
    public boolean stopChargingSession(long id) {
        return chargingSessionRepository.stopCharging(id);
    }


    /**
     * Gets charging summary.
     *
     * @return the charging summary
     */
    @Override
    public ChargingSummary getChargingSummary() {
        return chargingSessionRepository.getChargingSummary();
    }
}
