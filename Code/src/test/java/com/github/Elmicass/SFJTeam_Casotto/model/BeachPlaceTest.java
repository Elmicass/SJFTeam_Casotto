package com.github.Elmicass.SFJTeam_Casotto.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.github.Elmicass.SFJTeam_Casotto.exception.ReachedLimitOfObjects;
import com.google.zxing.WriterException;

import org.junit.jupiter.api.Test;

public class BeachPlaceTest {
    @Test
	void contextLoads() {
	}

	@Test
	void shouldConstructObjects() {
		SeaRow seaRow = new SeaRow(1, 10, 3.00);
		PriceList pl = new PriceList("TEST", 5.00, 5.00, 7.50, 10.00);
		try {
			BeachPlace bp = new BeachPlace(seaRow, 1, pl, "Small", 3);
			assertTrue(bp != null);
			int x = bp.hashCode();






		} catch (IllegalArgumentException | IllegalStateException | WriterException | IOException
				| ReachedLimitOfObjects e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
