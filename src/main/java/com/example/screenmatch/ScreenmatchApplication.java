package com.example.screenmatch;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.screenmatch.principal.Principal;

@SpringBootApplication
public class ScreenmatchApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		Principal iniciar = new Principal();
		
		iniciar.exibMenu();
		
	}

}
