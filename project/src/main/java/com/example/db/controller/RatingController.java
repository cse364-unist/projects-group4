package com.example.db.controller;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
import com.example.db.model.RecapObject;

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

    @GetMapping("/ratings/summary/{movieId}")
    Integer[] findByMovieId(@PathVariable String movieId) {
        List<Rating> l = repository.findAllByMovieId(movieId);
        Integer[] data = new Integer[6];
        for (int i = 0; i < 6; ++i) data[i] = 0;
        for (Rating it: l) {
            data[it.getRating()]++;
        }
        return data;
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

    // What recap includes in itself (by userId):
    // 1) Total number of ratings done by user in a certain period of time 
    // 2) Top 3 genres of movies (based on ratings done)
    // 3) Most popular movie user watched (popularity = count of ratings on that movie)

    // deltaTime is in seconds
    @GetMapping("/recap/{userId}")
    RecapObject getRecap(@PathVariable String userId, @RequestParam Long currentTime, @RequestParam Long deltaTime) {
        RecapObject ret = new RecapObject();
        // 1)
        // ratings -> timestamp \in [currentTime-deltaTime; currentTime]
        for (Rating it : repository.findAll()) {
            if (it.getUserId().equals(userId) && it.getTimestamp() >= currentTime-deltaTime) { 
                ret.setTotalRatings(ret.getTotalRatings()+1);
            }
        }

        // 2) maybe needs optimization (hash or something easy koroche)
        HashMap<String, Integer> count = new HashMap<>();
        for (Rating rating : repository.findAll()) {
            if (!rating.getUserId().equals(userId)) continue;

            Movie it = movieRepository.findById(rating.getMovieId()).get();
            String[] genres = it.getGenres().split("\\|", 0);
            for (String genre : genres) {
                int prevCount = 0;
                if (count.containsKey(genre)) {
                    prevCount = count.get(genre);
                }
                count.put(genre, prevCount+1);
            }
        }
        
        List<String> top3 = new LinkedList<>();
        for (String genre : count.keySet()) {
            top3.add(genre);
            if (top3.size() > 3) {
                String min = "";
                for (String x : top3) {
                    if (min.isEmpty() || count.get(min) > count.get(x)) {
                        min = x;
                    }
                }
                top3.remove(min);
            }
        }
        ret.setTop3genre(top3);
        
        // 3)
        count.clear();
        for (Rating rating : repository.findAll()) { 
            if (!rating.getUserId().equals(userId)) continue; 
            int prevCount = 0;
            if (count.containsKey(rating.getMovieId())) {
                prevCount = count.get(rating.getMovieId());
            }
            count.put(rating.getMovieId(), prevCount+1);
        }
        String mostPopular = "";
        for (Rating rating : repository.findAll()) { 
            if (!rating.getUserId().equals(userId)) continue; 
            if (mostPopular.isEmpty() || count.get(mostPopular) < count.get(rating.getMovieId())) {
                mostPopular = rating.getMovieId();
            } 
        }
        ret.setMostPopularMovieId(mostPopular);
        return ret;
    }
}