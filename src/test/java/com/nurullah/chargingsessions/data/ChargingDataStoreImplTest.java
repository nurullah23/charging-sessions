package com.nurullah.chargingsessions.data;

import static org.hamcrest.Matchers.equalTo;

import com.nurullah.chargingsessions.enums.ChargingStatus;
import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSummary;
import com.nurullah.chargingsessions.testutils.AppBaseTest;
import java.time.LocalDateTime;
import java.util.Random;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ChargingDataStoreImplTest extends AppBaseTest {

    @InjectMocks
    private ChargingDataStoreImpl dataStore;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void givenChargingSession_whenInsert_thenReturnTrue() {
        //Given
        ChargingSession chargingSession = mockChargingSession();

        //When
        boolean result = dataStore.insert(chargingSession);

        //Then
        collector.checkThat(result, equalTo(Boolean.TRUE));
    }

    @Test
    public void givenChargingSessionWithExistingId_whenInsert_thenReturnFalse() {
        //Given
        ChargingSession chargingSession = mockChargingSession();

        //When
        dataStore.insert(chargingSession);
        boolean result = dataStore.insert(chargingSession);

        //Then
        collector.checkThat(result, equalTo(Boolean.FALSE));
    }

    @Test
    public void givenExistingId_whenStopCharging_thenReturnTrue() {
        //Given
        ChargingSession chargingSession = mockChargingSession();

        //When
        dataStore.insert(chargingSession);
        boolean result = dataStore.stopCharging(SESSION_ID);

        //Then
        collector.checkThat(result, equalTo(Boolean.TRUE));
        collector.checkThat(chargingSession.getStatus(), equalTo(ChargingStatus.FINISHED));
    }

    @Test
    public void givenNonExistingId_whenStopCharging_thenReturnFalse() {
        //Given
        boolean result = dataStore.stopCharging(SESSION_ID);

        //Then
        collector.checkThat(result, equalTo(Boolean.FALSE));
    }

    @Test
    public void givenChargingSessions_whenGetChargingSummary_thenReturnSummary() {
        //Given
        LocalDateTime now = LocalDateTime.now();
        Random r = new Random();
        for(int i = 1; i <= 6; i++) {
            ChargingSession chargingSession = ChargingSession.builder()
                    .id((long) i)
                    .stationId(STATION_ID)
                    .startedAt(now.minusSeconds(r.nextInt(60)))
                    .status(ChargingStatus.IN_PROGRESS)
                    .build();
            dataStore.insert(chargingSession);
        }
        for(int i = 7; i <= 10; i++) {
            ChargingSession chargingSession = ChargingSession.builder()
                    .id((long) i)
                    .stationId(STATION_ID)
                    .startedAt(now.minusSeconds(r.nextInt(60)))
                    .status(ChargingStatus.FINISHED)
                    .build();
            dataStore.insert(chargingSession);
        }
        for(int i = 11; i <= 15; i++) {
            ChargingSession chargingSession = ChargingSession.builder()
                    .id((long) i)
                    .stationId(STATION_ID)
                    .startedAt(now.minusSeconds(60 + r.nextInt(i)))
                    .status(ChargingStatus.IN_PROGRESS)
                    .build();
            dataStore.insert(chargingSession);
        }

        //When
        ChargingSummary result = dataStore.getChargingSummary();

        //Then
        collector.checkThat(result.getStartedCount(), equalTo(6L));
        collector.checkThat(result.getStoppedCount(), equalTo(4L));
        collector.checkThat(result.getTotalCount(), equalTo(10L));
    }
}
