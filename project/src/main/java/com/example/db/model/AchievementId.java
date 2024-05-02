package com.example.db.model;

public class AchievementId {
    private String userId;
    private String achievement;

    public AchievementId() {}

    public AchievementId(String userId, String achievement) {
        this.userId = userId;
        this.achievement = achievement;
    }

    public void setUserId(String id) {
        userId = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setAchievement(String name) {
        achievement = name;
    }

    public String getAchievement() {
        return achievement;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AchievementId))
            return false;
        AchievementId other = (AchievementId)o;
        boolean userIdEquals = other.userId == this.userId;
        boolean achievementEquals = other.achievement == this.achievement;
        return userIdEquals && achievementEquals;
    }

    @Override 
    public final int hashCode() {
        String key = this.userId + "#" + this.achievement;
        return key.hashCode();
    }
}