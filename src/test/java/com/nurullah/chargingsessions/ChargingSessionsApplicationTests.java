package com.nurullah.chargingsessions;

import static org.assertj.core.api.Assertions.assertThat;

import com.nurullah.chargingsessions.controllers.ChargingSessionController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChargingSessionsApplicationTests {

	@Autowired
	private ChargingSessionController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
