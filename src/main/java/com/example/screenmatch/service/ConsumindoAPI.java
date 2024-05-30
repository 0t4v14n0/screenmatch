package com.example.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ConsumindoAPI {

	private static final String BASE_URL = "https://www.omdbapi.com/?t=";
	private static final String apiKey = "&apikey=c16acd6a";
    
    public String busca(String nome,String temporada,String episodio) throws IOException, InterruptedException { 

        String termoBuscaEncoded = URLEncoder.encode(nome, StandardCharsets.UTF_8);
        
        //inicia a variavel
        String endereco = "";
        
        //muda a chamada da url de acordo com as informacoes passadas
        if (temporada != "") {
        	        	
        	if(episodio != "") {
        		
        		endereco = BASE_URL + termoBuscaEncoded +"&season="+temporada+"&episode="+episodio+ apiKey;
        		
        	}else {
        		endereco = BASE_URL + termoBuscaEncoded +"&season="+temporada+ apiKey;
        	}
        	
        }else {
        	endereco = BASE_URL + termoBuscaEncoded + apiKey;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());   
                        
        String retorno = response.body();
        
        System.out.println(retorno);

        return retorno;
        
    }

}
