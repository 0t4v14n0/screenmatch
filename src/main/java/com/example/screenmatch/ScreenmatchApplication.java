package com.example.screenmatch;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.example.screenmatch.principal.Principal;
import com.example.screenmatch.principal.TestePrincipal;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		TestePrincipal principal = new TestePrincipal();
		principal.exibMenu();
	}

}
