package com.github.Elmicass.SFJTeam_Casotto;

import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CasottoApplication implements CommandLineRunner {

	@Autowired
	private ConsoleView consoleView;

	public static void main(String[] args) {
		SpringApplication.run(CasottoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		start();
	}

	public void start() {
		consoleView.open();
    }
	

}
