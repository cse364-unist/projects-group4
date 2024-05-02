package com.example.db.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.example.db.model.Achievement;
import com.example.db.model.AchievementId;

public interface AchievementRepository extends MongoRepository<Achievement, AchievementId> {
}