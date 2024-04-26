package com.example.db.model;

import org.springframework.data.annotation.Id;

public class Rating {
    @Id private String id;
    private String userId;
    private String movieId;
    private Integer rating;
    private Long timestamp;

    public Rating() {}

    public Rating(String userId, String movieId, Integer rating, Long timestamp) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}