package com.example.screenmatch;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.screenmatch.model.DadosEpisodio;
import com.example.screenmatch.model.DadosSerie;
import com.example.screenmatch.model.DadosTemporada;
import com.example.screenmatch.service.ConsumindoAPI;
import com.example.screenmatch.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		ConsumindoAPI api = new ConsumindoAPI();
		
		//pesquisa por nome
		String busca = api.busca("smallville","","");
		
		//System.out.println(busca);
		
		ConverteDados conversor = new ConverteDados();
		
		DadosSerie dados = conversor.obterDados(busca, DadosSerie.class);
			
		System.out.println(dados);
		
		//pesquisa por nome,temporada,episodio
		busca = api.busca("the office","4","10");
		
		DadosEpisodio dados2 = conversor.obterDados(busca, DadosEpisodio.class);
		
		System.out.println(dados2);
		
		//pesquisa por nome,temporada
		busca = api.busca("the office","","");
		
		DadosTemporada dados3 = conversor.obterDados(busca, DadosTemporada.class);
		
		System.out.println(dados3);
		
		// for percorrendo todos as temporadas
        for(int i = 1; i<=dados.totalTemporadas(); i++) {
        	
        	busca = api.busca("the office",""+i,"");
        	
        	DadosTemporada dados4 = conversor.obterDados(busca, DadosTemporada.class);
        	System.out.println(dados4);
        	
        	
        }
		
	}

}
