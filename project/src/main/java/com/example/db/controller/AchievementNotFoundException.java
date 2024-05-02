package com.example.db.controller;

class AchievementNotFoundException extends RuntimeException {
    AchievementNotFoundException() {
        super("Could not find achievement");
    }
}