package com.example.screenmatch.principal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.example.screenmatch.model.DadosSerie;
import com.example.screenmatch.model.DadosTemporada;
import com.example.screenmatch.model.Serie;
import com.example.screenmatch.repository.SerieRepository;
import com.example.screenmatch.service.ConsumindoAPI;
import com.example.screenmatch.service.ConverteDados;

public class TestePrincipal {
	
    private Scanner leitura = new Scanner(System.in); 
    
    private ConsumindoAPI consumo = new ConsumindoAPI();
    
    private ConverteDados conversor = new ConverteDados();
    
    private List<DadosSerie> dadosSerie = new ArrayList<>();
    
    private SerieRepository repositorio;
    
    public TestePrincipal(SerieRepository repositorio) {
    	this.repositorio = repositorio;
	}


	public void exibMenu(){
    	
    	var opcao = 4;
    	
    	do {

            System.out.println("""
                    1 - Buscar Séries
                    2 - Buscar Episódios
                    3 - Listar Series Buscadas
                    
                    0 - Sair                                 
                    """);
            
            opcao = leitura.nextInt();
            leitura.nextLine();
    		
    		
    		
            switch (opcao) {
            case 1:
                buscarSerieWeb();
                break;
            case 2:
                buscarEpisodioPorSerie();
                break;
            case 3:
            	listarSeriesBuscadas();
            	break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida");
        }
    		
    	}while(opcao != 0);    	
    	
    }
    
    
    private void listarSeriesBuscadas() {
    	
    	List<Serie> series = repositorio.findAll();
    	
		series.stream()
				.sorted(Comparator.comparing(Serie::getGenero))
				.forEach(System.out::println);
    }


	private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        System.out.println(serie);
        repositorio.save(serie);
        //dadosSerie.add(dados);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para a busca");
        
        var nomeSerie = leitura.nextLine();
        
        String json = retornaConsulta(nomeSerie,"","");
        
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        
        return dados;
    }

    private void buscarEpisodioPorSerie(){
    	
        DadosSerie dadosSerie = getDadosSerie();
        
        List<DadosTemporada> temporadas = new ArrayList<>();
        
        for(int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            
            String busca = retornaConsulta(dadosSerie.titulo(), "" + i, "");
            
            DadosTemporada temporada = conversor.obterDados(busca, DadosTemporada.class);
            
            //System.out.println(temporada);
            
            temporadas.add(temporada);
        }
        
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
    
    private String retornaConsulta(String nomeSerie,String temporada,String episodio) {
    	
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
