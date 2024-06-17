package com.example.screenmatch.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.screenmatch.dto.EpisodioDTO;
import com.example.screenmatch.dto.SerieDTO;
import com.example.screenmatch.repository.SerieRepository;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repositorio;
    
    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repositorio.findAll());
    }
    
    public List<SerieDTO> obterTop5Series() {
        return converteDados(repositorio.findTop5ByOrderByNotaDesc());
        
    }
        
    public List<SerieDTO> obterLancamentos() {
         return converteDados(repositorio.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }

	public SerieDTO obterPorId(Long id) {
		Optional<Serie> serie = repositorio.findById(id);
		if (serie.isPresent()) {
			Serie s = serie.get();
			return new SerieDTO(s.getId(),
					s.getTitulo(), 
					s.getAno(), 
					s.getTotalTemporadas(),
					s.getNota(), s.getData(), 
					s.getGenero(), s.getDiretor(),
					s.getAtor(), s.getSinopse(),
					s.getPoster());
		}
		return null;
	}
	
	public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);

        if (serie.isPresent()) {
                Serie s = serie.get();
                return s.getEpisodios().stream()
                                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                                .collect(Collectors.toList());
        }
        return null;
	}
	
    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                    .map(s -> new SerieDTO(s.getId(),
        					s.getTitulo(), 
        					s.getAno(), 
        					s.getTotalTemporadas(),
        					s.getNota(), s.getData(), 
        					s.getGenero(), s.getDiretor(),
        					s.getAtor(), s.getSinopse(),
        					s.getPoster()))
                    .collect(Collectors.toList());
    }
    
}
