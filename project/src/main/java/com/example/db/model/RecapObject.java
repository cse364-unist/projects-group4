package com.example.db.model;

import java.util.List;
import java.util.LinkedList;

// What recap includes in itself (by userId):
// 1) Total number of ratings done by user in a certain period of time 
// 2) Top 3 genres of movies (based on ratings done)
// 3) Most popular movie user watched (popularity = count of ratings on that movie)
// 4) ?? 
// 5) ??

public class RecapObject {
    private Long totalRatings;
    private List<String> top3genre;
    private String mostPopularMovieId;
    
    public RecapObject() {
        totalRatings = 0l;
        top3genre = new LinkedList<>();
        mostPopularMovieId = "";
    }

    public RecapObject(Long totalRatings, List<String> top3genre, String mostPopularMovieId) {
        this.totalRatings = totalRatings;
        this.top3genre = top3genre;
        this.mostPopularMovieId = mostPopularMovieId;
    }

    public Long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public List<String> getTop3genre() {
        return top3genre;
    }

    public void setTop3genre(List<String> top3genre) {
        this.top3genre = top3genre;
    }

    public String getMostPopularMovieId() {
        return mostPopularMovieId;
    }

    public void setMostPopularMovieId(String mostPopularMovieId) {
        this.mostPopularMovieId = mostPopularMovieId;
    }
}