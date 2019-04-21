package com.nurullah.chargingsessions.models;

import lombok.Builder;
import lombok.Data;

/**
 * The type Charging summary.
 */
@Builder
@Data
public class ChargingSummary {
    /**
     * The count of total charging sessions.
     */
    long totalCount;
    /**
     * The count of started charging sessions.
     */
    long startedCount;
    /**
     * The count of stopped charging sessions.
     */
    long stoppedCount;
}
