package com.nurullah.chargingsessions.models;

import com.nurullah.chargingsessions.enums.ChargingStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * The type Charging session.
 */
@Builder
@Data
public class ChargingSession implements Comparable<ChargingSession> {

    private long id;
    private long stationId;
    private LocalDateTime startedAt;
    private ChargingStatus status;

    /**
     * Constructs a ChargingSession instance from a {@link ChargingSessionDto}, setting status to In Progress
     * and startedAt to the date now.
     *
     * @param chargingSessionDto the charging session dto
     * @return the charging session instance
     */
    public static ChargingSession valueOf(ChargingSessionDto chargingSessionDto) {
        return ChargingSession.builder()
                .id(chargingSessionDto.getId())
                .stationId(chargingSessionDto.getStationId())
                .startedAt(LocalDateTime.now())
                .status(ChargingStatus.IN_PROGRESS)
                .build();
    }


    /**
     * The comparator describing charging session instances to be sorted for their startedAt values
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(ChargingSession o) {
        int result = this.startedAt.compareTo(o.startedAt);
        return result == 0 ? -1 : result;
    }
}
