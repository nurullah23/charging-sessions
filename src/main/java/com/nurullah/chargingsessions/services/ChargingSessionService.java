package com.nurullah.chargingsessions.services;

import com.nurullah.chargingsessions.models.ChargingSessionDto;
import com.nurullah.chargingsessions.models.ChargingSummary;
import org.springframework.stereotype.Service;

@Service
public interface ChargingSessionService {

    boolean createChargingSession(ChargingSessionDto chargingSessionDto);

    boolean stopChargingSession(long id);

    ChargingSummary getChargingSummary();
}
