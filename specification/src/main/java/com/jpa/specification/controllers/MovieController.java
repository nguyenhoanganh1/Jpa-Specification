package com.jpa.specification.controllers;

import com.jpa.specification.entities.Movie;
import com.jpa.specification.repositories.MovieRepository;
import com.jpa.specification.repositories.specifications.MovieSpecificationImpl;
import com.jpa.specification.repositories.specifications.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    @GetMapping("/get-all-movie")
    public ResponseEntity<?> getAllMovies(@RequestBody List<SearchCriteria> criteriaList){
        movieRepository.saveAll(Arrays.asList(
                new Movie("Troy", "Drama", 7.2, 196, 2004),
                new Movie("The Godfather", "Crime", 9.2, 178, 1972),
                new Movie("Invictus", "Sport", 7.3, 135, 2009),
                new Movie("Black Panther", "Action", 7.3, 135, 2018),
                new Movie("Joker", "Drama", 8.9, 122, 2018),
                new Movie("Iron Man", "Action", 8.9, 126, 2008)
        ));
        MovieSpecificationImpl msGenre = new MovieSpecificationImpl();
        criteriaList.forEach(search -> {
            msGenre.add(search);
        });
        List<Movie> msGenreList = movieRepository.findAll(msGenre);
        return ResponseEntity.ok(msGenreList);
    }
}
