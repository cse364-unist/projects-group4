package com.example.db.model;

import org.springframework.data.annotation.Id;
import com.example.db.model.AchievementId;

public class Achievement {
    @Id
    private AchievementId id;
    private Float progress;
    private Long timestamp;

    public Achievement() {}

    public Achievement(String userId, String achievementId, Float progress, Long timestamp) {
        this.id = new AchievementId(userId, achievementId);
        this.progress = progress;
        this.timestamp = timestamp;
    }

    public void update(Achievement other) {
        this.progress = other.progress;
        this.timestamp = other.timestamp;
    }

    public AchievementId getId() {
        return id;
    }

    public void setId(AchievementId id) {
        this.id = id;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}