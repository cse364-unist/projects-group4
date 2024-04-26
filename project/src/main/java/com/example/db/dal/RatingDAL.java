package com.example.db.dal;

import java.util.List;

import com.example.db.model.Rating;

public interface RatingDAL {

	List<Rating> getAllRatings();

	Rating getRatingById(String id);

	Rating addNewRating(Rating rating);
}