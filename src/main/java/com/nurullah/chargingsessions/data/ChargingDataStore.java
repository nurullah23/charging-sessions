package com.nurullah.chargingsessions.data;

import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSummary;

public interface ChargingDataStore {

    boolean insert(ChargingSession chargingSession);

    boolean stopCharging(long id);

    ChargingSummary getChargingSummary();
}
