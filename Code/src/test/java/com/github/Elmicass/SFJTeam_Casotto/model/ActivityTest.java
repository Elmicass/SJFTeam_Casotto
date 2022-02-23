package com.github.Elmicass.SFJTeam_Casotto.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;

import org.junit.jupiter.api.Test;

public class ActivityTest {
   	
	//tests of ID attribute
	/**@Test
	void shouldGetID(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		String count = "1";
		assertEquals(count, activity.getID());
	} */

	//tests of name attribute
	@Test
	void shouldGetName(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertEquals("test name",activity.getName());
	}

	@Test
	void shouldsetName(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		activity.setName("foo");
		assertEquals("foo",activity.getName());
	}

	@Test
	void shouldThrowIAExceptionWhenSetBlankName(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		assertThrows(IllegalArgumentException.class,() -> activity.setName(""));
	}

	@Test
	void shouldThrowIAExceptionWhenSetNullName(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		assertThrows(NullPointerException.class,() -> activity.setName(null));
	}

	//tests of timeSlot attribute
	@Test
	void shouldGetTimeSlot(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);
		
		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		TimeSlot timeSlot = new TimeSlot(ldtStart,ldtEnd,activity);
		assertEquals(timeSlot,activity.getTimeSlot());
	}

	@Test
	void shouldSetTimeSlot(){
		LocalDateTime ldtStart1 = LocalDateTime.now();
		LocalDateTime ldtEnd1 = ldtStart1.plusHours(3);

		LocalDateTime ldtStart2 = LocalDateTime.now().plusHours(1);
		LocalDateTime ldtEnd2 = ldtStart2.plusHours(5);

		

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart1,ldtEnd1,equipments);
		activity.setTimeSlot(ldtStart2,ldtEnd2);
		TimeSlot timeSlot2 = new TimeSlot(ldtStart2,ldtEnd2, activity);
		assertEquals(timeSlot2,activity.getTimeSlot());
	}

	@Test
	void shouldThrowNPExceptionWhenSetNullStartTimeSlot(){
		LocalDateTime ldtStart1 = LocalDateTime.now();
		LocalDateTime ldtEnd1 = ldtStart1.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart1,ldtEnd1,equipments);
		
		assertThrows(NullPointerException.class, () -> activity.setTimeSlot(null, ldtEnd1));
	}

	@Test
	void shouldThrowNPExceptionWhenSetNullEndTimeSlot(){
		LocalDateTime ldtStart1 = LocalDateTime.now();
		LocalDateTime ldtEnd1 = ldtStart1.plusHours(3);

		LocalDateTime ldtStart2 = LocalDateTime.now().plusHours(1);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart1,ldtEnd1,equipments);
		
		assertThrows(NullPointerException.class, () -> activity.setTimeSlot(ldtStart2,null));
	}

	@Test
	void shouldThrowNPExceptionWhenSetNullTimeSlot(){
		LocalDateTime ldtStart1 = LocalDateTime.now();
		LocalDateTime ldtEnd1 = ldtStart1.plusHours(3);


		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart1,ldtEnd1,equipments);
		
		assertThrows(NullPointerException.class, () -> activity.setTimeSlot(null,null));
	}

	//tests of description attribute
	@Test
	void shouldGetDescription(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertEquals("test description",activity.getDescription());
	}

	@Test
	void shouldSetDescription(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		activity.setDescription("foo");

		assertEquals("foo",activity.getDescription());
	}

	@Test
	void shouldThrowIAExceptionWhenSetBlankDescription(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertThrows(IllegalArgumentException.class, () -> activity.setDescription(""));
	}

	@Test
	void shouldThrowNPExceptionWhenSetNullDescription(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertThrows(NullPointerException.class, () -> activity.setDescription(null));
	}

	//tests of maxEntries attribute
	@Test
	void shouldGetMaxEntries(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertEquals(10,activity.getMaxEntries());
	}

	@Test
	void shouldSetMaxEntries(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		activity.setMaxEntries(1);
		assertEquals(1,activity.getMaxEntries());
	}

	@Test
	void shouldThrowNPExceptioNwhenSetNullMaxEntries(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		assertThrows(NullPointerException.class, () -> activity.setMaxEntries(null));
	}

	//tests of equipment attribute
	@Test
	void shouldGetEquipments(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertEquals(equipments,activity.getEquipments());
	}

	@Test
	void shouldThrowNPExceptionWhenSetNullEquipments(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		assertThrows(NullPointerException.class, () -> activity.setEquipments(null));
	}

	//tests of reservations attribute
	@Test
	void shouldGetReservation(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);

		assertEquals(0, activity.getReservations().size());
	}

	//tests of addEquipment method
	@Test
	void shouldAddEquipment(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		Equipment equipment2 = new Equipment("test name2","test description2","Outdoor");
		activity.addEquipment(equipment2);
		assertEquals(2,activity.getEquipments().size());
	}

	@Test
	void shouldThrowISExceptionWhenAddSameEquipment(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		assertThrows(IllegalStateException.class, () -> activity.addEquipment(equipment));
	}

	@Test
	void shouldThrowNPExceptionWhenAddNullEquipment(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		assertThrows(NullPointerException.class, () -> activity.addEquipment(null));
	}

	//tests of removeEquipment method
	@Test
	void shouldremoveEquipment(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment = new Equipment("test name","test description","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		activity.removeEquipment(equipment);
		assertEquals(0,activity.getEquipments().size());
	}

	@Test
	void shouldThrowISExceptionWhenRemoveDifferentEquipment(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		Equipment equipment2 = new Equipment("test name2","test description2","Outdoor");
		assertThrows(IllegalStateException.class, () -> activity.removeEquipment(equipment2));
	}

	@Test
	void shouldThrowNPExceptionWhenRemoveDifferentEquipment(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		assertThrows(NullPointerException.class, () -> activity.removeEquipment(null));
	}

	//tests of addReservation method
	@Test
	void shouldAddReservation() throws IllegalStateException, AlreadyExistingException{
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("testName","testDescription",10,ldtStart,ldtEnd,equipments);
		assertEquals(0,activity.getReservations().size());
		
		User user = new User("Simone","Micarelli","simone@test.it","xyz");
		User user2 = new User("Leon","Nowak","leon@test.it","xyz");

		activity.addReservation(new Reservation(BookableEntityType.Activity,user,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity));
		assertEquals(1,activity.getReservations().size());
		activity.addReservation(new Reservation(BookableEntityType.Activity,user2,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity));
		assertEquals(2, activity.getReservations().size());
	}

	@Test
	void shouldThrowAEExceptionWhenAddExistingUserReservation() throws IllegalStateException, AlreadyExistingException{
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("testName","testDescription",10,ldtStart,ldtEnd,equipments);
		assertEquals(0,activity.getReservations().size());

		User user = new User("Simone","Micarelli","simone@test.it","xyz");

		Reservation reservation = new Reservation(BookableEntityType.Activity,user,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity);
		activity.addReservation(reservation);
		assertEquals(1,activity.getReservations().size());
		Reservation reservation2 = new Reservation(BookableEntityType.Activity,user,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity);
		assertThrows(AlreadyExistingException.class, () -> activity.addReservation(reservation2));
	}

	@Test
	void shouldThrowISExceptionWhenMaxinumBookingNumberIsReachedInAddReservation() throws IllegalStateException, AlreadyExistingException{
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("test name","test description",1,ldtStart,ldtEnd,equipments);
		
		User user = new User("Simone","Micarelli","simone@test.it","xyz");
		User user2 = new User("Leon","Nowak","leon@test.it","xyz");

		Reservation reservation1 = new Reservation(BookableEntityType.Activity,user,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity);
		Reservation reservation2 = new Reservation(BookableEntityType.Activity,user2,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity);
		
		activity.addReservation(reservation1);
		assertThrows(IllegalStateException.class, () -> activity.addReservation(reservation2));

	}

	@Test
	void shouldThrowNPExceptionWhenAddNullReservation(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("test name","test description",1,ldtStart,ldtEnd,equipments);
		
		assertThrows(NullPointerException.class, () -> activity.addReservation(null));
	}

	//tests of removeReservation method
	@Test
	void shouldRemoveReservation() throws IllegalStateException, AlreadyExistingException{
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);

		User user = new User("Simone","Micarelli","simone@test.it","xyz");

		Reservation reservation = new Reservation(BookableEntityType.Activity,user,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity);

		activity.addReservation(reservation);
		assertFalse(activity.getReservations().isEmpty());
		assertTrue(activity.removeReservation(reservation));
		assertTrue(activity.getReservations().isEmpty());
	}
	
	@Test
	void shouldThrowISExceptionWhenRemoveDifferentReservation() throws IllegalStateException, AlreadyExistingException{
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);

		User user = new User("Simone","Micarelli","simone@test.it","xyz");
		User user2 = new User("Leon","Nowak","leon@test.it","xyz");

		Reservation reservation1 = new Reservation(BookableEntityType.Activity,user,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity);
		Reservation reservation2 = new Reservation(BookableEntityType.Activity,user2,activity.getTimeSlot().getStart(),activity.getTimeSlot().getStop(),activity);
		
		activity.addReservation(reservation1);
		assertThrows(IllegalStateException.class, () -> activity.removeReservation(reservation2));
		
	}

	@Test
	void shouldThrowNPExceptionWhenRemoveNullReservation(){
		LocalDateTime ldtStart = LocalDateTime.now();
		LocalDateTime ldtEnd = ldtStart.plusHours(3);

		Equipment equipment1 = new Equipment("test name1","test description1","Indoor");
		Set<Equipment> equipments = new HashSet<Equipment>();
		equipments.add(equipment1);
		Activity activity = new Activity("test name","test description",10,ldtStart,ldtEnd,equipments);
		
		assertThrows(NullPointerException.class, () -> activity.removeReservation(null));
	}
	
}