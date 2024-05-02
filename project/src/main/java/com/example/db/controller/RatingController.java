package com.example.db.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.db.model.Rating;
import com.example.db.model.Movie;
import com.example.db.repository.RatingRepository;
import com.example.db.repository.MovieRepository;
import com.example.db.repository.AchievementRepository;
import com.example.db.achievement.AchievementManager;
import com.example.db.controller.RatingNotFoundException;
import com.example.db.controller.RatingMovieWrongParamException;
import com.example.db.achievement.ActivityView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
class RatingController {
    private final RatingRepository repository;
    private final MovieRepository movieRepository;
    private final AchievementRepository achvRepository;
    private final AchievementManager achvManager;

    private static final Logger log = LoggerFactory.getLogger(RatingController.class);

    RatingController(
      RatingRepository repository, MovieRepository movieRepository, AchievementRepository achvRepository) {
        this.repository = repository;
        this.movieRepository = movieRepository;
        this.achvRepository = achvRepository;
        this.achvManager = AchievementManager.getInstance();
    }

    private void processEvent(String userId, Long timestamp) {
		List<Rating> ratings = repository.findAllByUserId(userId);
        List<String> movieIds = new ArrayList<String>();
        for (Rating elm: ratings) {
            movieIds.add(elm.getMovieId());
        }
        List<Movie> movies = movieRepository.findByIdIn(movieIds);
        ActivityView view = new ActivityView(movies);
        achvManager.update(achvRepository, userId, view, timestamp);
    }

    @GetMapping("/ratings")
    List<Rating> all() {
        return repository.findAll();
    }

    @PostMapping("/ratings")
    Rating newRating(@RequestBody Rating newRating) {
        Rating result = repository.save(newRating);
        processEvent(newRating.getUserId(), newRating.getTimestamp());
        return result;
    }

    @GetMapping("/ratings/minRating")
    List<Movie> getEqualOrGreaterMovies(@RequestParam Integer minRating) {
        if (minRating < 1 || minRating > 5)
            throw new RatingMovieWrongParamException();
        List<String> movieIds = repository.findMoviesWithRatingAtLeast(minRating);
        return movieRepository.findByIdIn(movieIds);
    }

    @GetMapping("/ratings/{id}")
    Rating one(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new RatingNotFoundException(id));
    }

    @PutMapping("/ratings/{id}")
    Rating replaceRating(@RequestBody Rating newRating, @PathVariable String id) {
        return repository.findById(id)
        .map(rating -> {
            rating.setMovieId(newRating.getMovieId());
            rating.setUserId(newRating.getUserId());
            rating.setRating(newRating.getRating());
            rating.setTimestamp(newRating.getTimestamp());
            return repository.save(rating);
        })
        .orElseGet(() -> {
            newRating.setId(id);
            return repository.save(newRating);
        });
    }

    @DeleteMapping("/ratings/{id}")
    void deleteRating(@PathVariable String id) {
        repository.deleteById(id);
    }
}