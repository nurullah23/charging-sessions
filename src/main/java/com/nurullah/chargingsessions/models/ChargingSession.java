package com.nurullah.chargingsessions.models;

import com.nurullah.chargingsessions.enums.ChargingStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChargingSession implements Comparable<ChargingSession> {

    private long id;
    private long stationId;
    private LocalDateTime startedAt;
    private ChargingStatus status;

    public static ChargingSession valueOf(ChargingSessionDto chargingSessionDto) {
        return ChargingSession.builder()
                .id(chargingSessionDto.getId())
                .stationId(chargingSessionDto.getStationId())
                .startedAt(LocalDateTime.now())
                .status(ChargingStatus.IN_PROGRESS)
                .build();
    }

    @Override
    public int compareTo(ChargingSession o) {
        int result = this.startedAt.compareTo(o.startedAt);
        return result == 0 ? -1 : result;
    }
}
