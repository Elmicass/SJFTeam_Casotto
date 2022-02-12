package com.github.Elmicass.SFJTeam_Casotto.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment.EquipmentType;

import org.junit.jupiter.api.Test;

public class EquipmentTest {
	
	//tests of ID attribute
	@Test
	void ShouldSetID(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		
		String count = "1";
		assertEquals(count, equipment.getID());
	}

	//tests of name attribute
	@Test
	void shouldGetName(){
		Equipment equipment = new Equipment("test name","test description","Indoor");

		assertEquals("test name",equipment.getName());
	}
	
	@Test
	void shouldsetName(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		equipment.setName("foo");
		assertEquals("foo",equipment.getName());
	}
	
	@Test
	void shouldThrowIAExceptionWhenSetBlankName(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		assertThrows(IllegalArgumentException.class,() -> equipment.setName(""));
	}
	
	@Test
	void shouldThrowIAExceptionWhenSetNullName(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		assertThrows(NullPointerException.class,() -> equipment.setName(null));
	}

	//tests of description attribute
	@Test
	void shouldGetDescription(){
		Equipment equipment = new Equipment("test name","test description","Indoor");

		assertEquals("test description",equipment.getDescription());
	}
	
	@Test
	void shouldsetDescription(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		equipment.setDescription("foo");
		assertEquals("foo",equipment.getDescription());
	}
	
	@Test
	void shouldThrowIAExceptionWhenSetBlankDescriprion(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		assertThrows(IllegalArgumentException.class,() -> equipment.setDescription(""));
	}
	
	@Test
	void shouldThrowIAExceptionWhenSetNullDescription(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		assertThrows(NullPointerException.class,() -> equipment.setDescription(null));
	}

	//tests of type attribute
	@Test
	void shouldGetType(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		assertEquals(EquipmentType.Indoor,equipment.getType());
	}
	
	@Test
	void shouldSetIndoorType(){
		Equipment equipment = new Equipment("test name","test description","Outdoor");
		equipment.setType("Indoor");
		assertEquals(EquipmentType.Indoor,equipment.getType());
	}
	
	@Test
	void shouldSetOutdoorType(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		equipment.setType("Outdoor");
		assertEquals(EquipmentType.Outdoor,equipment.getType());
	}
	
	@Test
	void shouldThrowIAExceptionWhenSetNonBinaryType(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		assertThrows(IllegalArgumentException.class, () -> equipment.setType("i am non binary type"));
	}

	//tests of isFree method
	@Test
	void shouldBeFree(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);
		TimeSlot timeSlot = new TimeSlot(ldtStart,ldtEnd);
		Equipment equipment = new Equipment("test name","test description","Indoor");
		
		assertTrue(equipment.isFree(timeSlot));
	}
	
	@Test
	void shouldntBeFree(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);
		TimeSlot timeSlot = new TimeSlot(ldtStart,ldtEnd);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtStart.plusHours(5),equipments);
		equipment.addActivity(activity);
		assertFalse(equipment.isFree(timeSlot));
	}

	@Test
	void shouldThrowNPExceptionWhenIsFreeOnNull(){
		LocalDateTime ldtStart = LocalDateTime.now();
		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtStart.plusHours(5),equipments);
		equipment.addActivity(activity);
		assertThrows(NullPointerException.class, () -> equipment.isFree(null));
	}

	//tests of addActivity method
	@Test
	void shouldAddActivity(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);
		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertTrue(equipment.getActivity().isEmpty());
		equipment.addActivity(activity);
		assertFalse(equipment.getActivity().isEmpty());
	}

	//tests of removeActivity method
	@Test
	void shouldRemoveActivity(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);
		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		equipment.addActivity(activity);
		assertFalse(equipment.getActivity().isEmpty());
		equipment.removeActivity(activity);
		assertTrue(equipment.getActivity().isEmpty());
		
	}
	
	@Test
	void shouldThrowIAExceptionWhenRemoveNonScheduledActivity(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);
		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertThrows(IllegalArgumentException.class, () -> equipment.removeActivity(activity));
	}
	
	@Test
	void shouldThrowNPExceptionWhenRemoveNullActivity(){
		Equipment equipment = new Equipment("test name","test description","Indoor");
		assertThrows(NullPointerException.class, () -> equipment.removeActivity(null));
	}
}
