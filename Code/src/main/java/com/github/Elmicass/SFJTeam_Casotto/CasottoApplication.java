package com.github.Elmicass.SFJTeam_Casotto;

import javax.security.auth.Subject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CasottoApplication implements CommandLineRunner {

	private static ThreadLocal<Subject> currentAppUser;

	public static void main(String[] args) {
		SpringApplication.run(CasottoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
	

}
