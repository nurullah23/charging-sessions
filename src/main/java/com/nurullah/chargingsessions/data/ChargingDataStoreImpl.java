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

@Component
public class ChargingDataStoreImpl implements ChargingDataStore {
    private final NavigableSet<ChargingSession> chargingSessions = new ConcurrentSkipListSet<>(Comparator.reverseOrder());
    private final ConcurrentMap<Long, ChargingSession> chargingSessionMap = new ConcurrentHashMap<>();

    @Override
    public boolean insert(ChargingSession chargingSession) {
        boolean result = this.chargingSessionMap.get(chargingSession.getId()) == null;
        this.chargingSessionMap.computeIfAbsent(chargingSession.getId(), (id) -> {
            this.chargingSessions.add(chargingSession);
            return chargingSession;
        });
        return result;
    }

    @Override
    public boolean stopCharging(long id) {
        return this.chargingSessionMap.computeIfPresent(id, (sessionId, chargingSession) -> {
            // TODO Could: Add endedAt, consumption
            chargingSession.setStatus(ChargingStatus.FINISHED);
            return chargingSession;
        }) != null;
    }

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
