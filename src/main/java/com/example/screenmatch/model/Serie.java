package com.example.screenmatch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.example.screenmatch.service.ConsultaChatGPT;

@Entity
@Table(name = "series")
public class Serie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String titulo;
	
	private String ano;
	private Integer totalTemporadas;
	private double nota;
	private String data;
	
	@Enumerated(EnumType.STRING)
	private Categoria genero;
	
	private String diretor;
	private String ator;
	private String sinopse;
	private String poster;
	
	@OneToMany(mappedBy = "serie")
	private List<Episodio> episodio = new ArrayList<>();
	
	public Serie() {}
	
	public Serie(DadosSerie dadosSerie) {
		
		this.titulo = dadosSerie.titulo();
		this.ano = dadosSerie.ano();
		this.totalTemporadas = dadosSerie.totalTemporadas();
		this.nota = OptionalDouble.of(Double.valueOf(dadosSerie.nota())).orElse(0);
		this.data = dadosSerie.data();
		this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim()); //split divide as informaoes em um array|| trim tira espaco em branco ficando oq inporta
		this.diretor = dadosSerie.diretor();
		this.ator = dadosSerie.ator();
		this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
		this.poster = dadosSerie.poster();
		
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Episodio> getEpisodio() {
		return episodio;
	}

	public void setEpisodio(List<Episodio> episodio) {
		this.episodio = episodio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Integer getTotalTemporadas() {
		return totalTemporadas;
	}

	public void setTotalTemporadas(Integer totalTemporadas) {
		this.totalTemporadas = totalTemporadas;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Categoria getGenero() {
		return genero;
	}

	public void setGenero(Categoria genero) {
		this.genero = genero;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getAtor() {
		return ator;
	}

	public void setAtor(String ator) {
		this.ator = ator;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	public String toString() {
		return ""+"Tittulo = '"+titulo+"'\''"+
				", Ano = '"+ano+"'\''"+
				", Total Temporadas = '"+totalTemporadas+"'\''"+
				", Avaliacao = '"+nota+'\''+
				", Data = '"+data+"'\''"+
				", Genero = '"+genero+"'\''"+
				", Diretor = '"+diretor+"'\''"+
				", Ator = '"+ator+"'\''"+
				", Sinopse = '"+sinopse+"'\''"+
				", Poster = '"+poster+"'\''";
	}
}
