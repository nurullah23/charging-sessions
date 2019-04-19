package com.nurullah.chargingsessions.services;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.nurullah.chargingsessions.enums.ChargingStatus;
import com.nurullah.chargingsessions.models.ChargingSession;
import com.nurullah.chargingsessions.models.ChargingSessionDto;
import com.nurullah.chargingsessions.models.ChargingSummary;
import com.nurullah.chargingsessions.repositories.ChargingSessionRepository;
import com.nurullah.chargingsessions.testutils.AppBaseTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ChargingSessionServiceImplTest extends AppBaseTest {

    @Mock
    private ChargingSessionRepository chargingSessionRepository;

    @InjectMocks
    private ChargingSessionServiceImpl service;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Captor
    private ArgumentCaptor<ChargingSession> captor;

    @Test
    public void givenChargingSessionDto_whenCreateChargingSession_thenReturnTrue() {
        //Given
        ChargingSessionDto chargingSessionDto = mockChargingSessionDto();
        doReturn(true).when(chargingSessionRepository).insert(any(ChargingSession.class));

        //When
        boolean result = service.createChargingSession(chargingSessionDto);

        //Then
        verify(chargingSessionRepository).insert(captor.capture());
        ChargingSession value = captor.getValue();
        collector.checkThat(result, equalTo(Boolean.TRUE));
        collector.checkThat(value.getId(), equalTo(chargingSessionDto.getId()));
        collector.checkThat(value.getStationId(), equalTo(chargingSessionDto.getStationId()));
        collector.checkThat(value.getStatus(), equalTo(ChargingStatus.IN_PROGRESS));
    }

    @Test
    public void givenSessionId_whenStopChargingSession_thenReturnTrue() {
        //Given
        doReturn(true).when(chargingSessionRepository).stopCharging(SESSION_ID);

        //When
        boolean result = service.stopChargingSession(SESSION_ID);

        //Then
        verify(chargingSessionRepository).stopCharging(SESSION_ID);
        collector.checkThat(result, equalTo(Boolean.TRUE));
    }

    @Test
    public void givenGetChargingSummary_thenReturnSummary() {
        //Given
        ChargingSummary summary = mockChargingSummary();
        doReturn(summary).when(chargingSessionRepository).getChargingSummary();

        //When
        ChargingSummary result = service.getChargingSummary();

        //Then
        verify(chargingSessionRepository).getChargingSummary();
        collector.checkThat(result, equalTo(summary));
    }
}
