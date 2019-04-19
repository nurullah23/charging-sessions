package com.nurullah.chargingsessions.services;

import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSessionDto;
import com.nurullah.chargingsessions.models.ChargingSummary;
import com.nurullah.chargingsessions.repositories.ChargingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {

    private ChargingSessionRepository chargingSessionRepository;

    @Autowired
    public ChargingSessionServiceImpl(ChargingSessionRepository chargingSessionRepository) {
        this.chargingSessionRepository = chargingSessionRepository;
    }

    @Override
    public boolean createChargingSession(ChargingSessionDto chargingSessionDto) {
        ChargingSession chargingSession = ChargingSession.valueOf(chargingSessionDto);
        return chargingSessionRepository.insert(chargingSession);
    }

    @Override
    public boolean stopChargingSession(long id) {
        return chargingSessionRepository.stopCharging(id);
    }

    @Override
    public ChargingSummary getChargingSummary() {
        return chargingSessionRepository.getChargingSummary();
    }
}
