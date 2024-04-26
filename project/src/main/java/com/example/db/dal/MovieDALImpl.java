package com.example.db.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.db.model.Movie;
import com.example.db.dal.MovieDAL;

@Repository
public class MovieDALImpl implements MovieDAL {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Movie> getAllMovies() {
		return mongoTemplate.findAll(Movie.class);
	}

	@Override
	public Movie getMovieById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Movie.class);
	}

	@Override
	public Movie addNewMovie(Movie movie) {
		mongoTemplate.save(movie);
		return movie;
	}
}