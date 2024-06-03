package com.example.screenmatch.principal;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.example.screenmatch.model.DadosEpisodio;
import com.example.screenmatch.model.DadosSerie;
import com.example.screenmatch.model.DadosTemporada;
import com.example.screenmatch.model.Episodio;
import com.example.screenmatch.service.ConsumindoAPI;
import com.example.screenmatch.service.ConverteDados;

public class Principal {
	
    private Scanner leitura = new Scanner(System.in); 
    
    private ConsumindoAPI consumo = new ConsumindoAPI();
    
    private ConverteDados conversor = new ConverteDados();

    public void exibMenu(){
    	
            System.out.println("Digite o nome da série para a busca");
            
            var nomeSerie = leitura.nextLine();
            
            String json = retornaConsulta(nomeSerie,"","");
            
            System.out.println(json);
            
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            
            List<DadosTemporada> temporadas = new ArrayList<>();
            
            for(int i = 1; i <= dados.totalTemporadas(); i++) {
                
                String busca = retornaConsulta(nomeSerie, "" + i, "");
                
                DadosTemporada temporada = conversor.obterDados(busca, DadosTemporada.class);
                
                System.out.println(temporada);
                
                temporadas.add(temporada);
            }
            
            temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
            
            List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream())
                    .collect(Collectors.toList());
            
                    
            System.out.println("\n Top 5 episódios");
            dadosEpisodios.stream()
            	.filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
            	.sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
            	.limit(5)
            	.forEach(System.out::println);
            
            System.out.println("\n Lista de Episodios: ");
            
            //classe EPISODIO
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                    		.map(d -> new Episodio(t.numero(),d))
                    		).collect(Collectors.toList());
            
            episodios.forEach(System.out::println);
            
            
            System.out.println("A partir de que ano você deseja ver os episódios? ");
            var ano = leitura.nextInt();
            leitura.nextLine();

            LocalDate dataBusca = LocalDate.of(ano, 1, 1);

            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            episodios.stream()
                    .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                    .forEach(e -> System.out.println(
                            "Temporada:  " + e.getTemporada() +
                                    " Episódio: " + e.getTitulo() +
                                    " Data lançamento: " + e.getDataLancamento().format(formatador)
                    ));      
            
    }
    
    public String retornaConsulta(String nomeSerie,String temporada,String episodio) {
    	
    	String retorno = "";
    	
        try {
			retorno = consumo.busca(nomeSerie, temporada, episodio);
			System.out.println(retorno);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
           
		return retorno;
		
    }

}
