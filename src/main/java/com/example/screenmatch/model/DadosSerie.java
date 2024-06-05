package com.example.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
						@JsonAlias("Year") String ano,
						@JsonAlias("totalSeasons") Integer totalTemporadas,
						@JsonAlias("imdbRating") String nota,
						@JsonAlias("Released") String data,
						@JsonAlias("Runtime") String tempo,
						@JsonAlias("Genre") String genero,
						@JsonAlias("Director") String diretor,
						@JsonAlias("Actors") String ator,
						@JsonAlias("Plot") String sinopse,
						@JsonAlias("Poster") String poster) {}
