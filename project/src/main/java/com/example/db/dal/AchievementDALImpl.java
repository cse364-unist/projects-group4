package com.example.db.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.db.model.Achievement;
import com.example.db.model.AchievementId;
import com.example.db.dal.AchievementDAL;

public class AchievementDALImpl implements AchievementDAL {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Achievement> getAllAchievements() {
		return mongoTemplate.findAll(Achievement.class);
	}

	// @Override
	// public List<Achievement> getAchievementsByCriteria(String userId, String achievement) {
	// 	Query query = new Query();
	// 	AchievementId id = new AchievementId(userId, achievement);
	// 	query.addCriteria(Criteria.where("id").is(id));
	// 	return mongoTemplate.findAll(query, Achievement.class);
	// }
	
	@Override
	public Achievement getAchievementById(AchievementId id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Achievement.class);
	}

	@Override
	public Achievement addAchievement(Achievement achievement) {
		mongoTemplate.save(achievement);
		return achievement;
	}
}