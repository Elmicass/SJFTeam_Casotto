package com.github.Elmicass.SFJTeam_Casotto.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

public class RoleTest {
    
	/**

	//tests of name attribute
	@Test
	void shouldGetName(){
		Role role = new Role("test name");
		assertEquals("test name",role.getName());
	}
	@Test
	void shouldSetName(){
		Role role = new Role("test name");
		role.setName("foo");
		assertEquals("foo",role.getName());
	}

	//tests of getUsers method
	@Test
	void shouldGetUsers(){
		Role role = new Role("test name");
		User user = new User("test name","test surname","test email");

		assertTrue(role.getUsers().isEmpty());
		role.addUser(user);
		assertFalse(role.getUsers().isEmpty());
	}

	//tests of setUsers method
	@Test
	void shouldSetUsers(){
		Set<User> users = new HashSet<User>();
		users.add(new User("test name","test surname","test email"));

		Role role = new Role("test name");
		role.setUsers(users);
		assertEquals(users,role.getUsers());

	}
	@Test
	void shouldThrowNPExceptionWhenSetNullUsers(){
		Role role = new Role("test name");
		assertThrows(NullPointerException.class, () -> role.setUsers(null));
	}

	//tests of getPrivileges method
	@Test
	void shouldGetPrivileges(){
		Role role = new Role("test name");

		assertTrue(role.getPrivileges().isEmpty());
		Set<Privilege> privileges = new HashSet<Privilege>();
		privileges.add(new Privilege("test name"));

		role.setPrivileges(privileges);
		assertFalse(role.getPrivileges().isEmpty());
	}

	//tests of setPrivileges method
	@Test
	void shouldSetPrivileges(){
		Role role = new Role("test name");

		Set<Privilege> privileges = new HashSet<Privilege>();
		privileges.add(new Privilege("test name"));
		role.setPrivileges(privileges);
		assertEquals(privileges,role.getPrivileges());
	}
	@Test
	void shouldThrowNPExceptionWhenSetNullPrivileges(){
		Role role = new Role("test name");
		assertThrows(NullPointerException.class, () -> role.setPrivileges(null));
	}
	
	//tests of addUser method
	@Test
	void shouldAddUser(){
		Role role = new Role("test name");

		assertTrue(role.getUsers().isEmpty());
		User user = new User("test name","test surname","test email");
		role.addUser(user);
		assertTrue(role.getUsers().contains(user));
	}
	@Test
	void shouldThrowISExceptionWhenAddSameUser(){
		Role role = new Role("test name");
		User user = new User("test name","test surname","test email");
		role.addUser(user);
		assertThrows(IllegalStateException.class, () -> role.addUser(user));
	}
	@Test
	void shouldThrowNPExceptionWhenAddNullUser(){
		Role role = new Role("test name");
		assertThrows(NullPointerException.class, () -> role.addUser(null));
	}

	//tests of removeUser method
	@Test
	void shouldRemoveUser(){
		Role role = new Role("test role");

		User user = new User("test name","test surname","test email");
		role.addUser(user);
		assertTrue(role.getUsers().contains(user));

		role.removeUser(user);
		assertFalse(role.getUsers().contains(user));
	}
	@Test
	void shouldThrowISExceptionWhenRemoveDifferentUser(){
		User user1 = new User("test name1","test surname1","test email1");
		User user2 = new User("test name2","test surname2","test email2");

		Role role = new Role("test role");

		role.addUser(user1);
		assertThrows(IllegalStateException.class, () -> role.removeUser(user2));
	}
	@Test
	void shouldThrowNPExceptionWhenRemoveNullUser(){
		Role role = new Role("test role");
		assertThrows(NullPointerException.class, () -> role.removeUser(null));
	}

	//tests of addPrivilege method
	@Test
	void shouldAddPrivilege(){
		Role role = new Role("test role");
		
		Privilege privilege = new Privilege("test name");
		assertFalse(role.getPrivileges().contains(privilege));
		role.addPrivilege(privilege);
		assertTrue(role.getPrivileges().contains(privilege));
	}
	@Test
	void shouldThrowISExceptionWhenAddSamePrivilege(){
		Role role = new Role("test role");
		Privilege privilege = new Privilege("test name");

		role.addPrivilege(privilege);
		assertThrows(IllegalStateException.class, () -> role.addPrivilege(privilege));
	}
	@Test
	void shouldThrowNPExceptionWhenAddNullPrivilege(){
		Role role = new Role("test role");
		assertThrows(NullPointerException.class, () -> role.addPrivilege(null));
	}

	//tests of removePrivilege method
	@Test
	void shouldRemovePrivilege(){
		Role role = new Role("test role");
		Privilege privilege = new Privilege("test name");
		role.addPrivilege(privilege);

		assertTrue(role.getPrivileges().contains(privilege));

		role.removePrivilege(privilege);
		assertFalse(role.getPrivileges().contains(privilege));
	}
	@Test
	void shouldThrowISExceptionWhenRemoveSamePrivilege(){
		Role role = new Role("test role");
		Privilege privilege = new Privilege("test name");
		assertThrows(IllegalStateException.class, () -> role.removePrivilege(privilege));
	}
	@Test
	void shouldThrowNPExceptionWhenRemoveNullPrivilege(){
		Role role = new Role("test role");
		assertThrows(NullPointerException.class, () -> role.removePrivilege(null));
	}

	*/

}
