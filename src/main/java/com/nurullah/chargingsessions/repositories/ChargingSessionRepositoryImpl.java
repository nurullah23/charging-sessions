package com.nurullah.chargingsessions.repositories;

import com.nurullah.chargingsessions.data.ChargingDataStore;
import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link ChargingSessionRepository}
 *
 * Responsible from dealing with the data layer
 */
@Component
public class ChargingSessionRepositoryImpl implements ChargingSessionRepository {

    private ChargingDataStore dataStore;

    /**
     * Instantiates a new Charging session repository.
     *
     * @param dataStore the data store
     */
    @Autowired
    public ChargingSessionRepositoryImpl(ChargingDataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Insert boolean.
     *
     * @param chargingSession the charging session
     * @return <code>true</code> if new charging session is added successfully, otherwise <code>falue</code>.
     */
    @Override
    public boolean insert(ChargingSession chargingSession) {
        return dataStore.insert(chargingSession);
    }

    /**
     * Stop charging boolean.
     *
     * @param id charging session id
     * @return <code>true</code> if value is changed, otherwise <code>false</code>
     */
    @Override
    public boolean stopCharging(long id) {
        return dataStore.stopCharging(id);
    }

    /**
     * Gets charging summary.
     *
     * @return the charging summary
     */
    @Override
    public ChargingSummary getChargingSummary() {
        return dataStore.getChargingSummary();
    }
}
