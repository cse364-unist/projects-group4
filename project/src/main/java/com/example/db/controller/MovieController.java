package com.example.db.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.db.model.Movie;
import com.example.db.repository.MovieRepository;
import com.example.db.controller.MovieNotFoundException;

@RestController
class MovieController {
    private final MovieRepository repository;

    MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/movies")
    List<Movie> all() {
        return repository.findAll();
    }

    @PostMapping("/movies")
    Movie newMovie(@RequestBody Movie newMovie) {
        return repository.save(newMovie);
    }

    @GetMapping("/movies/{id}")
    Movie one(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @PutMapping("/movies/{id}")
    Movie replaceMovie(@RequestBody Movie newMovie, @PathVariable String id) {
        return repository.findById(id)
        .map(movie -> {
            movie.setTitle(newMovie.getTitle());
            movie.setGenres(newMovie.getGenres());
            return repository.save(movie);
        })
        .orElseGet(() -> {
            newMovie.setId(id);
            return repository.save(newMovie);
        });
    }

    @DeleteMapping("/movies/{id}")
    void deleteMovie(@PathVariable String id) {
        repository.deleteById(id);
    }
}