package com.example.screenmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.screenmatch.dto.EpisodioDTO;
import com.example.screenmatch.dto.SerieDTO;
import com.example.screenmatch.model.SerieService;

@RestController
@RequestMapping("/series")
public class SerieController {

   @Autowired
   private SerieService servico;

    @GetMapping
    public List<SerieDTO> obterSeries() {
       return servico.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return servico.obterTop5Series();
    }
    
    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return servico.obterLancamentos();
    }
    
    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
    	return servico.obterPorId(id);
    }
    
    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
            return servico.obterTodasTemporadas(id);
    }
    
}
