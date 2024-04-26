package com.example.db.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

import com.example.db.model.Rating;
import com.example.db.repository.RatingRepositoryCustom;

@Repository
public class RatingRepositoryCustomImpl implements RatingRepositoryCustom {

    private final MongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(RatingRepositoryCustomImpl.class);

    @Autowired
    public RatingRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<String> findMoviesWithRatingAtLeast(int minRating) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group("movieId").avg("rating").as("avgRating"),
            Aggregation.match(Criteria.where("avgRating").gte(minRating)),
            Aggregation.project("movieId")
        );

        AggregationResults<String> results = mongoTemplate.aggregate(aggregation, Rating.class, String.class);
        List<String> movieIds = results.getMappedResults().stream()
            .map(input -> input.substring(input.indexOf(":") + 1, input.length() - 1).trim())
            .collect(Collectors.toList());
        
        return movieIds;
    }
}