package com.github.Elmicass.SFJTeam_Casotto.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SeaRowTest {
    
	//tests of seaRowNumber attribute
	@Test
	void shouldGetSeaRowNumber(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertEquals(1, seaRow.getSeaRowNumber());
	}

	@Test
	void shouldSetSeaRowNumber(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		seaRow.setSeaRowNumber(2);
		assertEquals(2, seaRow.getSeaRowNumber());
	}

	@Test
	void shouldThrowIAExceptionWhenSetZeroSeaRowNumber(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertThrows(IllegalArgumentException.class,() -> seaRow.setSeaRowNumber(0));
	}

	@Test
	void shouldThrowNPExceptionWhenSetNullSeaRowNumber(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertThrows(NullPointerException.class,() -> seaRow.setSeaRowNumber(null));
	}

	//tests of maxbeachplaces attribute
	@Test
	void shouldGetMaxBeachPlacesInThisRow(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertEquals(1,seaRow.getMaxBeachPlacesInThisRow());
	}

	@Test
	void shouldSetMaxBeachPlacesInThisRow(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		seaRow.setMaxBeachPlacesInThisRow(2);
		assertEquals(2,seaRow.getMaxBeachPlacesInThisRow());
	}

	@Test
	void shouldThrowIAExceptionWhenSetZeroMaxBeachPlacesInThisRow(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertThrows(IllegalArgumentException.class, () -> seaRow.setMaxBeachPlacesInThisRow(0));
	}

	@Test
	void shouldThrowNPExceptionWhenSetNullMaxBeachPlacesInThisRow(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertThrows(NullPointerException.class, () -> seaRow.setMaxBeachPlacesInThisRow(null));
	}

	//tests of availablebeachplacespositions
	@Test
	void shouldGetAvailableBeachPlacesPositions(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertEquals(1,seaRow.getAvailableBeachPlacesPositions());
	}

	@Test
	void shouldSetAvailableBeachPlacesPositions(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		seaRow.setAvailableBeachPlacesPositions(2);
		assertEquals(2,seaRow.getAvailableBeachPlacesPositions());
	}

	@Test
	void shouldThrowNPExceptionWhenSetNullAvailableBeachPlacesPositions(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertThrows(NullPointerException.class, () -> seaRow.setAvailableBeachPlacesPositions(null));
	}

	//tests of fixedprice attribute
	@Test
	void shouldGetFixedPrice(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertEquals(1.0,seaRow.getFixedPrice());
	}

	@Test
	void shouldSetFixedPrice(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		seaRow.setFixedPrice(2.0);
		assertEquals(2.0,seaRow.getFixedPrice());
	}
	
	@Test
	void shouldThrowNPExceptionWhenSetNullFixedPrice(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertThrows(NullPointerException.class, () -> seaRow.setFixedPrice(null));
	}

	//tests of searowmap attribute
	@Test
	void shouldGetSeaRowMap(){
		SeaRow seaRow = new SeaRow(1,1,1.0);
		assertNotNull(seaRow.getSeaRowMap());
	}
}
