package com.example.screenmatch.principal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.example.screenmatch.model.Categoria;
import com.example.screenmatch.model.DadosSerie;
import com.example.screenmatch.model.DadosTemporada;
import com.example.screenmatch.model.Episodio;
import com.example.screenmatch.model.Serie;
import com.example.screenmatch.repository.SerieRepository;
import com.example.screenmatch.service.ConsumindoAPI;
import com.example.screenmatch.service.ConverteDados;

public class TestePrincipal {
	
    private Scanner leitura = new Scanner(System.in); 
    
    private ConsumindoAPI consumo = new ConsumindoAPI();
    
    private ConverteDados conversor = new ConverteDados();
    
    private SerieRepository repositorio;
    
    private List<Serie> series = new ArrayList<>();
    
    private Optional<Serie> serieBusca;
    
    public TestePrincipal(SerieRepository repositorio) {
    	this.repositorio = repositorio;
	}

	public void exibMenu(){
    	
    	var opcao = 4;
    	
    	do {

            System.out.println("""
            		
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar séries por ator
                    6 - Top 5 Séries
                    7 - Buscar séries por categoria
                    8 - Filtrar séries
                    9 - Buscar episódios por trecho
                    10 - Top 5 episódios por série
                    11 - Buscar episódios a partir de uma data 
                                    
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
            case 4:
                buscarSeriePorTitulo();
                break;
            case 5:
                buscarSeriesPorAtor();
                break;
            case 6:
                buscarTop5Series();
                break;
            case 7:
                buscarSeriesPorCategoria();
                break;
            case 8:
                filtrarSeriesPorTemporadaEAvaliacao();
                break;
            case 9:
                buscarEpisodioPorTrecho();
                break;
            case 10:
                topEpisodiosPorSerie();
                break;
            case 11:
                buscarEpisodiosDepoisDeUmaData();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida");
        }
    		
    	}while(opcao != 0);    	
    	
    }
     
    private void buscarEpisodiosDepoisDeUmaData() {
    	
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            System.out.println("Digite o ano limite de lançamento");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosAno = repositorio.episodiosPorSerieEAno(serie, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
		
	}

	private void topEpisodiosPorSerie() {
    	
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
		
	}

	private void buscarEpisodioPorTrecho() {
    	
        System.out.println("Qual o nome do episódio para busca?");
        var trechoEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));
		
	}

	private void filtrarSeriesPorTemporadaEAvaliacao() {
    	
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaEAValiacao(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getNota()));
		
	}

	private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar séries de que categoria/gênero? ");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Séries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
		
	}

	private void buscarTop5Series() {
        List<Serie> serieTop = repositorio.findTop5ByOrderByNotaDesc();
        serieTop.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getNota()));
		
	}

	private void buscarSeriesPorAtor() {
    	
        System.out.println("Qual o nome para busca?");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de que valor? ");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtorContainingIgnoreCaseAndNotaGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getNota()));		
	}

	private void buscarSeriePorTitulo() {
    	
        System.out.println("Escolha um série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());

        } else {
            System.out.println("Série não encontrada!");
        }
		
	}

	private void listarSeriesBuscadas() {
    	
    	series = repositorio.findAll();
    	
		series.stream()
				.sorted(Comparator.comparing(Serie::getGenero))
				.forEach(System.out::println);
    }

	private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        System.out.println(serie);
        repositorio.save(serie);
        //System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para a busca");
        
        var nomeSerie = leitura.nextLine();
        
        String json = retornaConsulta(nomeSerie,"","");
        
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        
        return dados;
    }

    private void buscarEpisodioPorSerie(){
    	
    	listarSeriesBuscadas();
    	
    	System.out.println("Qual e o nome do episodio ?");
    	var nomeSerie = leitura.nextLine();
    	
    	Optional<Serie>serie = series.stream().
    		filter(s ->s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
    		.findFirst();
    	
    	if(serie.isPresent()) {
    		
    		var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();
            
            for(int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                
                String busca = retornaConsulta(serieEncontrada.getTitulo(), "" + i, "");
                
                DadosTemporada temporada = conversor.obterDados(busca, DadosTemporada.class);
                
                temporadas.add(temporada);
            }
            
            temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    		
            List<Episodio> episodios = temporadas.stream()
            	.flatMap(d -> d.episodios().stream()
            		.map(e -> new Episodio(d.numero(), e)))
            			.collect(Collectors.toList());
            
            serieEncontrada.setEpisodios(episodios);
            
            repositorio.save(serieEncontrada);
            
    	}
    	else {
        	System.out.println("Serie nao encontrada");
        }
        
    }
    
    private String retornaConsulta(String nomeSerie,String temporada,String episodio) {
    	
    	String retorno = "";
    	
        try {
			retorno = consumo.busca(nomeSerie, temporada, episodio);
			//System.out.println(retorno);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
           
		return retorno;
		
    }

}
