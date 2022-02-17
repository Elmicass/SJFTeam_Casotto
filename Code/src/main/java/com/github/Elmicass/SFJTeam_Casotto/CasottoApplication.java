package com.github.Elmicass.SFJTeam_Casotto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleView;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class CasottoApplication implements CommandLineRunner {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
            try {
				ConfigurableApplicationContext context = SpringApplication.run(CasottoApplication.class, args);
				ConsoleView view = context.getBean(ConsoleView.class);
				view.open();
			} catch (Throwable e) {
				System.out.println("FATAL ERROR - Build NOT Successful: terminating Spring application");
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String sStackTrace = sw.toString(); // stack trace as a string
				System.out.println(sStackTrace);
			}
        } else {
            System.err.println("Boot failed. \n");
        }
	}

	@Override
	public void run(String... args) throws Exception {
	}


}
