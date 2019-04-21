package com.nurullah.chargingsessions.data;

import com.nurullah.chargingsessions.enums.ChargingStatus;
import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSummary;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link ChargingDataStore}
 *
 * This class stores the charging sessions. {@link ChargingDataStoreImpl#chargingSessions} stores the charging session
 * instances in a thread safe sorted set. They are sorted by startedAt values.
 */
@Component
public class ChargingDataStoreImpl implements ChargingDataStore {
    /**
     * {@link ChargingDataStoreImpl#chargingSessions} stores the charging session instances in a thread safe sorted set.
     * They are sorted by startedAt values.
     */
    private final NavigableSet<ChargingSession> chargingSessions = new ConcurrentSkipListSet<>(Comparator.reverseOrder());

    /**
     * {@link ChargingDataStoreImpl#chargingSessionMap} stores the charging session instances as id and charging session pairs.
     */
    private final ConcurrentMap<Long, ChargingSession> chargingSessionMap = new ConcurrentHashMap<>();


    /**
     * Insert a new ChargingSession into both chargingSessions and chargingSessionMap if charging session id is not already in the chargingSessionMap
     *
     * @param chargingSession the charging session
     * @return <code>true</code> if new charging session is added successfully, otherwise <code>falue</code>.
     */
    @Override
    public boolean insert(ChargingSession chargingSession) {
        boolean result = this.chargingSessionMap.get(chargingSession.getId()) == null;
        this.chargingSessionMap.computeIfAbsent(chargingSession.getId(), (id) -> {
            this.chargingSessions.add(chargingSession);
            return chargingSession;
        });
        return result;
    }


    /**
     * If present, changes the charging session status with given id to {@link ChargingStatus#FINISHED}.
     * Since the instance is the same in both chargingSessions and chargingSessionMap, this operation takes only O(1)
     *
     * @param id charging session id
     * @return <code>true</code> if value is changed, otherwise <code>false</code>
     */
    @Override
    public boolean stopCharging(long id) {
        return this.chargingSessionMap.computeIfPresent(id, (sessionId, chargingSession) -> {
            // TODO Could: Add endedAt, consumption
            chargingSession.setStatus(ChargingStatus.FINISHED);
            return chargingSession;
        }) != null;
    }

    /**
     * Gets the charging sessions summary for the last minute.
     *
     * @return the charging summary
     */
    @Override
    public ChargingSummary getChargingSummary() {

        synchronized (this.chargingSessions) {
            LocalDateTime aMinuteAgo = LocalDateTime.now().minusMinutes(1);
            Iterator<ChargingSession> iterator = this.chargingSessions.iterator();
            long startedCount = 0,
                 stoppedCount = 0;
            while (iterator.hasNext()) {
                ChargingSession session = iterator.next();

                if (session.getStartedAt().isBefore(aMinuteAgo)) {
                    break;
                }

                switch (session.getStatus()) {
                    case IN_PROGRESS:
                        startedCount++;
                        break;
                    case FINISHED:
                        stoppedCount++;
                        break;
                    default:
                        throw new IllegalStateException("Invalid charging status: " + session.getStatus());
                }
            }

            return ChargingSummary.builder()
                    .startedCount(startedCount)
                    .stoppedCount(stoppedCount)
                    .totalCount(startedCount + stoppedCount)
                    .build();
        }
    }

}
