package com.example.screenmatch;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.screenmatch.model.DadosSerie;
import com.example.screenmatch.service.ConsumindoAPI;
import com.example.screenmatch.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		ConsumindoAPI api = new ConsumindoAPI();
		
		String busca = api.busca("Joker");
		
		//System.out.println(busca);
		
		ConverteDados conversor = new ConverteDados();
		
		DadosSerie dados = conversor.obterDados(busca, DadosSerie.class);
		
		System.out.println(dados);
		
	}

}
