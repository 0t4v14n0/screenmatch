package com.example.screenmatch.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.screenmatch.dto.SerieDTO;
import com.example.screenmatch.repository.SerieRepository;

@RestController
public class SerieController {
	
	@Autowired
	private SerieRepository repositorio;
	
	@GetMapping("/series")
	public List<SerieDTO> obterSeries() {
	    return repositorio.findAll()
	            .stream()
	            .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getAno(), s.getTotalTemporadas(), s.getNota(), s.getData(), s.getGenero(), s.getAtor(), s.getPoster(), s.getSinopse(),s.getPoster()))
	            .collect(Collectors.toList());
	    }

}
