package com.example.db.achievement.logic;

import com.example.db.achievement.ActivityView;
import com.example.db.achievement.logic.AchievementLogic;
// import com.example.db.achievement.AchievementManager;

public class ReviewTen implements AchievementLogic {
    // private static final ReviewOne singleton = new ReviewOne();

    // private ReviewOne() {
    //     AchievementManager manager = AchievementManager.getInstance();
    //     manager.addLogic(singleton);
    // }
    public ReviewTen() {}
    @Override
    public Float getProgress(ActivityView view) {
        return view.getTotalReviews() >= 10 ? (float)100: (float)view.getTotalReviews() * 10;
    }
    @Override
    public String getName() {
        return "ReviewTen";
    }
    @Override
    public String getDescription() {
        return "Review at least ten movies.";
    }
}