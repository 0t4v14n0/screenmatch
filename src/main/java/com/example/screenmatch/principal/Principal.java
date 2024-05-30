package com.example.screenmatch.principal;

import java.io.IOException;
import java.util.Scanner;

import com.example.screenmatch.service.ConsumindoAPI;
import com.example.screenmatch.service.ConverteDados;

public class Principal {
	
    private Scanner leitura = new Scanner(System.in); 
    
    private ConsumindoAPI consumo = new ConsumindoAPI();
    
    private ConverteDados conversor = new ConverteDados();

    public void exibMenu(){
    	
            System.out.println("Digite o nome da série para a busca");
            
            var nomeSerie = leitura.nextLine();
            
            System.out.println(retornaConsulta(nomeSerie,"",""));
            
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
