package com.example.db.dal;

import java.util.List;

import com.example.db.model.Achievement;
import com.example.db.model.AchievementId;

public interface AchievementDAL {

	List<Achievement> getAllAchievements();

	Achievement getAchievementById(AchievementId id);

	Achievement addAchievement(Achievement achievement);
}