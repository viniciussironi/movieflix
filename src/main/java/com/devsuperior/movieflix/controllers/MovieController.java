package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
    public ResponseEntity<Page<MovieCardDTO>> searchByGenreId(@RequestParam (name = "genreId", defaultValue = "")Long id, Pageable pageable) {
        if(id == null) {
            PageRequest request = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title"));
            return ResponseEntity.ok().body(service.findAll(request));
        }
        PageRequest page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title"));
        return ResponseEntity.ok().body(service.searchByGenreId(id, pageable));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
    public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }
}
