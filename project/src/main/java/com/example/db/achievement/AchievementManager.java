package com.example.db.achievement;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.db.achievement.logic.AchievementLogic;
import com.example.db.achievement.logic.ReviewOne;
import com.example.db.achievement.logic.ReviewTen;
import com.example.db.achievement.logic.BigFiveOne;
import com.example.db.achievement.logic.BigFiveTen;
import com.example.db.achievement.ActivityView;
import com.example.db.repository.AchievementRepository;
import com.example.db.model.AchievementId;
import com.example.db.model.Achievement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AchievementManager {
    private static final AchievementManager instance = new AchievementManager();
    private List<AchievementLogic> logics;
    private static final Logger log = LoggerFactory.getLogger(AchievementManager.class);
    
    private AchievementManager() {
        this.logics = new ArrayList<AchievementLogic>();
        addLogic(new ReviewOne());
        addLogic(new ReviewTen());
        addLogic(new BigFiveOne());
        addLogic(new BigFiveTen());
    }

    public static AchievementManager getInstance() {
        return instance;
    }

    public void addLogic(AchievementLogic logic) {
        // log.info(logic.getName());
        logics.add(logic);
    }

    public Float getProgress(AchievementRepository repository, AchievementId id) {
        Optional<Achievement> elm = repository.findById(id);
        return elm.isPresent() ? elm.get().getProgress(): 0;
    }

    public void setAchievement(
      AchievementRepository repository, 
      AchievementId id, Float progress, Long timestamp) {  
        Achievement elm = new Achievement();
        elm.setId(id);
        elm.setProgress(progress);
        elm.setTimestamp(timestamp);
        repository.save(elm);
    }

    public void update(AchievementRepository repository, String userId, ActivityView view, Long timestamp) {
        // log.info(view.getTotalReviews().toString());
        // String[] genres = {"Action", "Adventure", "Comedy", "Drama", "Romance"};
        // log.info(view.genreCount.toString());
        // for (String genre: genres) {
        //     log.info(view.getGenreCount(genre).toString());
        // }

        for (AchievementLogic logic: logics) {
            AchievementId id = new AchievementId(userId, logic.getName());
            Float oldProgress = this.getProgress(repository, id);
            if (oldProgress >= 100.0)
                continue;
            Float newProgress = logic.getProgress(view);
            if (oldProgress < newProgress) {
                this.setAchievement(repository, id, newProgress, timestamp);
            }
        }
    }
}