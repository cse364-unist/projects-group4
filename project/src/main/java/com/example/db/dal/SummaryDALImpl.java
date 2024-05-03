package com.example.db.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.db.model.Summary;
import com.example.db.dal.SummaryDAL;

@Repository
public class SummaryDALImpl implements SummaryDAL {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Summary> getAllSummaries() {
		return mongoTemplate.findAll(Summary.class);
	}

	@Override
	public Summary getSummaryById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Summary.class);
	}

	@Override
	public Summary addNewSummary(Summary summary) {
		mongoTemplate.save(summary);
		return summary;
	}
}