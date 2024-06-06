package com.example.screenmatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.example.screenmatch.principal.Principal;
import com.example.screenmatch.principal.TestePrincipal;
import com.example.screenmatch.repository.SerieRepository;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{
	
    @Autowired
    private SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		TestePrincipal principal = new TestePrincipal(repositorio);
		principal.exibMenu();
	}

}
