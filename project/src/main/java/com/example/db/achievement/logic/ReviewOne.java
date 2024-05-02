package com.example.db.achievement.logic;

import com.example.db.achievement.ActivityView;
import com.example.db.achievement.logic.AchievementLogic;
// import com.example.db.achievement.AchievementManager;

public class ReviewOne implements AchievementLogic {
    // private static final ReviewOne singleton = new ReviewOne();

    // private ReviewOne() {
    //     AchievementManager manager = AchievementManager.getInstance();
    //     manager.addLogic(singleton);
    // }
    public ReviewOne() {}
    @Override
    public Float getProgress(ActivityView view) {
        return view.getTotalReviews() >= 1 ? (float)100: (float)0;
    }
    @Override
    public String getName() {
        return "ReviewOne";
    }
    @Override
    public String getDescription() {
        return "Review at least one movie.";
    }
}