package com.example.db.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.db.model.Rating;
import com.example.db.dal.RatingDAL;

@Repository
public class RatingDALImpl implements RatingDAL {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Rating> getAllRatings() {
		return mongoTemplate.findAll(Rating.class);
	}

	@Override
	public Rating getRatingById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Rating.class);
	}

	@Override
	public Rating addNewRating(Rating rating) {
		mongoTemplate.save(rating);
		return rating;
	}
}