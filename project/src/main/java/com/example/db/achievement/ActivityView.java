package com.example.db.achievement;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.example.db.model.Movie;

public class ActivityView {
    private Integer totalReviews;
    public Map<String, Integer> genreCount;

    public ActivityView (List<Movie> movies) {
        totalReviews = movies.size();
        genreCount = new HashMap<String, Integer>();
        for (Movie elm: movies) {
            String[] genres = elm.getGenres().split("\\|", -1);
            for (String genre: genres) {
                Integer count = this.getGenreCount(genre);
                genreCount.put(genre, count + 1);
            }
        }
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public Integer getGenreCount(String genre) {
        return genreCount.containsKey(genre) ? genreCount.get(genre): 0;
    }
}