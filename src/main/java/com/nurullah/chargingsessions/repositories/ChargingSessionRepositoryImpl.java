package com.nurullah.chargingsessions.repositories;

import com.nurullah.chargingsessions.data.ChargingDataStore;
import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChargingSessionRepositoryImpl implements ChargingSessionRepository {

    private ChargingDataStore dataStore;

    @Autowired
    public ChargingSessionRepositoryImpl(ChargingDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public boolean insert(ChargingSession chargingSession) {
        return dataStore.insert(chargingSession);
    }

    @Override
    public boolean stopCharging(long id) {
        return dataStore.stopCharging(id);
    }

    @Override
    public ChargingSummary getChargingSummary() {
        return dataStore.getChargingSummary();
    }
}
