package com.nurullah.chargingsessions.repositories;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.nurullah.chargingsessions.data.ChargingDataStore;
import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSummary;
import com.nurullah.chargingsessions.testutils.AppBaseTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ChargingSessionRepositoryImplTest extends AppBaseTest {

    @Mock
    private ChargingDataStore dataStore;

    @InjectMocks
    private ChargingSessionRepositoryImpl repository;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void givenChargingSession_whenInsert_thenReturnTrue() {
        //Given
        ChargingSession chargingSession = mockChargingSession();
        doReturn(true).when(dataStore).insert(chargingSession);

        //When
        boolean result = repository.insert(chargingSession);

        //Then
        verify(dataStore).insert(chargingSession);
        collector.checkThat(result, equalTo(Boolean.TRUE));
    }

    @Test
    public void givenSessionId_whenStopCharging_thenReturnTrue() {
        //Given
        doReturn(true).when(dataStore).stopCharging(SESSION_ID);

        //When
        boolean result = repository.stopCharging(SESSION_ID);

        //Then
        verify(dataStore).stopCharging(SESSION_ID);
        collector.checkThat(result, equalTo(Boolean.TRUE));
    }

    @Test
    public void givenGetChargingSummary_thenReturnTrue() {
        //Given
        ChargingSummary chargingSummary = mockChargingSummary();
        doReturn(chargingSummary).when(dataStore).getChargingSummary();

        //When
        ChargingSummary result = repository.getChargingSummary();

        //Then
        verify(dataStore).getChargingSummary();
        collector.checkThat(result, equalTo(chargingSummary));
    }
}
