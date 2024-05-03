package com.example.db.achievement.logic;

import com.example.db.achievement.ActivityView;
import com.example.db.achievement.logic.AchievementLogic;
// import com.example.db.achievement.AchievementManager;

public class BigFiveOne implements AchievementLogic {
    // private static final ReviewOne singleton = new ReviewOne();

    // private ReviewOne() {
    //     AchievementManager manager = AchievementManager.getInstance();
    //     manager.addLogic(singleton);
    // }

    private String[] genres = {"Action", "Adventure", "Comedy", "Drama", "Romance"};

    public BigFiveOne() {}
    @Override
    public Float getProgress(ActivityView view) {
        int count = 0;
        int limit = 1;
        for (String genre: genres) {
            if (view.getGenreCount(genre) >= limit)
                count += 1;
        }
        return count >= 5 ? (float)100: (float)20 * count;
    }
    @Override
    public String getName() {
        return "BigFiveOne";
    }
    @Override
    public String getDescription() {
        return "Review Each of Five Main Genres at least once: Action, Adventure, Comedy, Drama, Romance.";
    }
}