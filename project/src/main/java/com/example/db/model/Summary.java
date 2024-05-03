package com.example.db.model;

import org.springframework.data.annotation.Id;

public class Summary {
    @Id private String id;
    private String movieId;
    private Long timestamp;
    private String summaryText;

    public Summary() {}

    public Summary(String id, String movieId, Long timestamp, String summaryText) {
        this.id = id;
        this.movieId = movieId;
        this.timestamp = timestamp;
        this.summaryText = summaryText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }
}
