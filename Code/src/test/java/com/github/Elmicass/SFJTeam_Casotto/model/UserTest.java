package com.github.Elmicass.SFJTeam_Casotto.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class UserTest {
    //tests of ID attribute
	@Test
	void shouldGetID(){
		User user = new User("test name","test surname","test email");
		assertNotNull(user.getID());
	}

	//tests of name attribute
	@Test
	void shouldGetName(){
		User user = new User("test name","test surname","test email");
		assertNotNull(user.getName());
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
		assertNotNull(user.getSurname());
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
		assertNotNull(user.getEmail());
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
	@Test

	//tests of role attribute
	void shouldGetRole(){
		User user = new User("test name","test surname","test email");
		assertNotNull(user.getRole());
	}
	
}