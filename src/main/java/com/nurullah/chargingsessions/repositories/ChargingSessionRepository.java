package com.nurullah.chargingsessions.repositories;

import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSummary;

public interface ChargingSessionRepository {

    boolean insert(ChargingSession chargingSession);

    boolean stopCharging(long id);

    ChargingSummary getChargingSummary();
}
