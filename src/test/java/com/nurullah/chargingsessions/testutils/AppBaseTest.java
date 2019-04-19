package com.nurullah.chargingsessions.testutils;

import com.nurullah.chargingsessions.enums.ChargingStatus;
import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSessionDto;
import com.nurullah.chargingsessions.models.ChargingSummary;
import java.time.LocalDateTime;

public class AppBaseTest {

    protected static final long SESSION_ID = 1L;
    protected static final long STATION_ID = 101L;
    protected static final LocalDateTime TIME_STARTED = LocalDateTime.now();

    protected ChargingSessionDto mockChargingSessionDto() {
        return new ChargingSessionDto(SESSION_ID, STATION_ID);
    }

    protected ChargingSession mockChargingSession() {
        return ChargingSession.builder()
                .id(SESSION_ID)
                .stationId(STATION_ID)
                .startedAt(TIME_STARTED)
                .status(ChargingStatus.IN_PROGRESS)
                .build();
    }

    protected ChargingSession mockChargingSessionWithoutTime() {
        return ChargingSession.builder()
                .id(SESSION_ID)
                .stationId(STATION_ID)
                .build();
    }

    protected ChargingSummary mockChargingSummary() {
        return ChargingSummary.builder()
                .startedCount(1L)
                .stoppedCount(2L)
                .totalCount(3L)
                .build();
    }
}
