package com.nurullah.chargingsessions.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChargingSummary {
    long totalCount;
    long startedCount;
    long stoppedCount;
}
