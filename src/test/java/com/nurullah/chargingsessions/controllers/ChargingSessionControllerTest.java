package com.nurullah.chargingsessions.controllers;


import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nurullah.chargingsessions.models.ChargingSessionDto;
import com.nurullah.chargingsessions.models.ChargingSummary;
import com.nurullah.chargingsessions.services.ChargingSessionService;
import com.nurullah.chargingsessions.testutils.AppBaseTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(ChargingSessionController.class)
public class ChargingSessionControllerTest extends AppBaseTest {

    private static final String URL_CHARGING_SESSION = "/chargingSession";
    private static final String URL_CHARGING_SESSION_WITH_PARAM = "/chargingSession/1";
    private static final String URL_CHARGING_SESSIONS = "/chargingSessions";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ChargingSessionService chargingSessionService;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Test
    public void givenChargingSession_whenSubmitChargingSession_thenReturnSuccess() throws Exception {
        //Given
        final ChargingSessionDto session = new ChargingSessionDto(SESSION_ID, STATION_ID);
        final String jsonContent = "{\"id\":1, \"stationId\": 101}";
        doReturn(true).when(chargingSessionService).createChargingSession(session);

        //Then
        mvc.perform(post(URL_CHARGING_SESSION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(chargingSessionService).createChargingSession(session);
    }

    @Test
    public void givenChargingSession_whenStopChargingSession_thenReturnSuccess() throws Exception {
        //Given
        doReturn(true).when(chargingSessionService).stopChargingSession(SESSION_ID);

        //Then
        mvc.perform(put(URL_CHARGING_SESSION_WITH_PARAM)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(chargingSessionService).stopChargingSession(SESSION_ID);
    }

    @Test
    public void givenGetChargingSummary_thenReturnSummary() throws Exception {
        //Given
        ChargingSummary summary = mockChargingSummary();
        String jsonResponse = "{\"totalCount\":3,\"startedCount\":1,\"stoppedCount\":2}";
        doReturn(summary).when(chargingSessionService).getChargingSummary();

        //Then
        final MvcResult result = mvc.perform(get(URL_CHARGING_SESSIONS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(chargingSessionService).getChargingSummary();
        collector.checkThat(result.getResponse().getContentAsString(), equalTo(jsonResponse));
    }

}
