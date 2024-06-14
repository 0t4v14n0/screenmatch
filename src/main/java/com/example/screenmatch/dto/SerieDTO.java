package com.example.screenmatch.dto;

import com.example.screenmatch.model.Categoria;

public record SerieDTO(Long id,	
						String titulo,
						String ano,
						Integer totalTemporadas,
						double nota,
						String data,
						Categoria genero,
						String diretor,
						String ator,
						String sinopse,
						String poster) {}
