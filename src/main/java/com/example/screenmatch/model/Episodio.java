package com.example.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
	
	private Integer temporada;
	private String titulo;
	private Integer numeroEpisodio;       
	private Double avaliacao;
	private LocalDate dataLancamento;

	public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodios) {
		
		this.temporada      = numeroTemporada;
		this.titulo         = dadosEpisodios.titulo();
		this.numeroEpisodio = dadosEpisodios.numero();
        try {
            this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
        } catch (DateTimeParseException ex) {
            this.dataLancamento = null;
        }
	}

	public Integer getTemporada() {
		return temporada;
	}
	
	public void setTemporada(Integer temporada) {
		this.temporada = temporada;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Integer getnumeroEpisodio() {
		return numeroEpisodio;
	}
	
	public void setnumeroEpisodio(Integer numeroEpisodio) {
		this.numeroEpisodio = numeroEpisodio;
	}
	
	public Double getAvaliacao() {
		return avaliacao;
	}
	
	public void setAvaliacao(Double avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	public LocalDate getDataLancamento() {
		return dataLancamento;
	}
	
	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
	public String toString() {
		return "temporada = "+temporada+
				", titulo = "+titulo+'\''+
				", numeroEpisodio = "+numeroEpisodio+'\''+
				", avaliacao = "+avaliacao+'\''+
				", dataLancamento = "+dataLancamento+'\'';
	}
		
}
