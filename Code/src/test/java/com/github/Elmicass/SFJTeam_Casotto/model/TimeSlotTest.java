package com.github.Elmicass.SFJTeam_Casotto.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TimeSlotTest {
    @Test
	void contextLoads() {
	}

	/**
	@Test
	void testOverlapping() {
		TimeSlot ts1 = new TimeSlot(LocalDateTime.of(2022, 02, 20, 9, 00) , LocalDateTime.of(2022, 02, 22, 9, 00));
		TimeSlot ts2 = new TimeSlot(LocalDateTime.of(2022, 02, 25, 9, 00) , LocalDateTime.of(2022, 03, 25, 9, 00));
		boolean result = ts1.overlapsWith(ts2);
		assertFalse(result);
	}
	*/


	
}
