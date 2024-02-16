package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> searchByGenreId(Long id, Pageable pageable) {
        Page<MovieProjection> page = movieRepository.searchByGenreId(id, pageable);
        return page.map(x -> new MovieCardDTO(x));
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findAll(Pageable pageable) {
        Page<Movie> page = movieRepository.findAll(pageable);
        return page.map(x -> new MovieCardDTO(x));
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById (Long id) {
        Movie entity = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não encontrado"));
        return new MovieDetailsDTO(entity);
    }
}
