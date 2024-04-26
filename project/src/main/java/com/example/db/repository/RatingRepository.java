package com.example.db.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.example.db.model.Rating;
import com.example.db.model.Movie;
import com.example.db.repository.RatingRepositoryCustom;

public interface RatingRepository extends MongoRepository<Rating, String>, RatingRepositoryCustom {
}