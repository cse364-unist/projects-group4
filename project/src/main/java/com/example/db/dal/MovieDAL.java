package com.example.db.dal;

import java.util.List;

import com.example.db.model.Movie;

public interface MovieDAL {

	List<Movie> getAllMovies();

	Movie getMovieById(String id);

	Movie addNewMovie(Movie movie);
}