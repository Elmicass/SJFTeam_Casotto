package com.github.Elmicass.SFJTeam_Casotto.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class UserTest {
    
	/**

	//tests of ID attribute
	@Test
	void shouldGetID(){
		User user = new User("test name","test surname","test email");
		
		assertEquals(user.count.toString(), user.getID());
	}

	//tests of name attribute
	@Test
	void shouldGetName(){
		User user = new User("test name","test surname","test email");
		
		assertEquals("test name",user.getName());
	}
	@Test
	void shouldsetName(){
		User user = new User("test name","test surname","test email");
		user.setName("foo");
		assertEquals("foo",user.getName());
	}
	@Test
	void shouldThrowIAExceptionWhenSetBlankName(){
		User user = new User("test name","test surname","test email");
		assertThrows(IllegalArgumentException.class,() -> user.setName(""));
	}
	@Test
	void shouldThrowIAExceptionWhenSetNullName(){
		User user = new User("test name","test surname","test email");
		assertThrows(NullPointerException.class,() -> user.setName(null));
	}

	//tests of surname attribute
	@Test
	void shouldGetSurame(){
		User user = new User("test name","test surname","test email");
		assertEquals("test surname",user.getSurname());
	}
	@Test
	void shouldsetSurname(){
		User user = new User("test name","test surname","test email");
		user.setSurname("foo");
		assertEquals("foo",user.getSurname());
	}
	@Test
	void shouldThrowIAExceptionWhenSetBlankSurname(){
		User user = new User("test name","test surname","test email");
		assertThrows(IllegalArgumentException.class,() -> user.setSurname(""));
	}
	@Test
	void shouldThrowIAExceptionWhenSetNullSurname(){
		User user = new User("test name","test surname","test email");
		assertThrows(NullPointerException.class,() -> user.setSurname(null));
	}
	
	//tests of surname attribute
	@Test
	void shouldGetEmail(){
		User user = new User("test name","test surname","test email");
		assertEquals("test email",user.getEmail());
	}
	@Test
	void shouldsetEmail(){
		User user = new User("test name","test surname","test email");
		user.setEmail("foo");
		assertEquals("foo",user.getEmail());
	}
	@Test
	void shouldThrowIAExceptionWhenSetBlankEmail(){
		User user = new User("test name","test surname","test email");
		assertThrows(IllegalArgumentException.class,() -> user.setEmail(""));
	}
	@Test
	void shouldThrowNPExceptionWhenSetNullEmail(){
		User user = new User("test name","test surname","test email");
		assertThrows(NullPointerException.class,() -> user.setEmail(null));
	}
	

	//tests of role attribute
	@Test
	void shouldGetRole(){
		User user = new User("test name","test surname","test email");
		assertNotNull(user.getRole());
	}
	@Test
	void shouldSetRole(){
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role("test role"));

		User user = new User("test name","test surname","test email");
		user.setRoles(roles);
		assertEquals(roles,user.getRole());
	}
	@Test
	void shouldThrowNPExceptionWhenSetNullRoles(){
		User user = new User("test name","test surname","test email");
		assertThrows(NullPointerException.class, () -> user.setRoles(null));
	}

	//tests of addRole method
	@Test
	void shouldAddRole(){
		Role role = new Role("test role");

		User user = new User("test name","test surname","test email");
		assertTrue(user.getRole().isEmpty());
		user.addRole(role);

		assertTrue(user.getRole().contains(role));
	}
	@Test
	void shouldThrowISExceptionWhenAddIdenticalRole(){
		Role role = new Role("test role");

		User user = new User("test name","test surname","test email");
		user.addRole(role);
		assertThrows(IllegalStateException.class, () -> user.addRole(role));
	}
	@Test
	void shouldNPExceptionWhenAddNullRole(){
		User user = new User("test name","test surname","test email");
		assertThrows(NullPointerException.class, () -> user.addRole(null));
	}

	//tests of removeRole method
	@Test
	void shouldRemoveRole(){
		Role role = new Role("test role");

		User user = new User("test name","test surname","test email");
		user.addRole(role);
		assertTrue(user.getRole().contains(role));

		user.removeRole(role);
		assertFalse(user.getRole().contains(role));
	}
	@Test
	void shouldThrowISExceptionWhenRemoveDifferentRole(){
		Role role1 = new Role("test role1");
		Role role2 = new Role("test role2");

		User user = new User("test name","test surname","test email");
		user.addRole(role1);
		assertThrows(IllegalStateException.class, () -> user.removeRole(role2));
	}
	@Test
	void shouldThrowNPExceptionWhenRemoveNullRole(){
		User user = new User("test name","test surname","test email");
		assertThrows(NullPointerException.class, () ->user.removeRole(null));
	}

	*/
}