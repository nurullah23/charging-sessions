package com.nurullah.chargingsessions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Charging session dto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChargingSessionDto {
    private long id;
    private long stationId;
}
