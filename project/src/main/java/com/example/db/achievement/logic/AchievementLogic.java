package com.example.db.achievement.logic;

import com.example.db.achievement.ActivityView;

public interface AchievementLogic {
    Float getProgress(ActivityView view);
    String getName();
    String getDescription();
}