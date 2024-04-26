package com.example.db.model;

import org.springframework.data.annotation.Id;

public class Movie {
    @Id private String id;
    private String title;
    private String genres;

    public Movie() {}

    public Movie(String id, String title, String genres) {
        this.id = id;
        this.title = title;
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}