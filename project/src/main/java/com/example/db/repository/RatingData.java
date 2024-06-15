package com.example.db.model;

public class RatingData {
    private Character gender;
    private Integer age;
    private Integer score;

    public RatingData(Character gender, Integer age, Integer score) {
        this.gender = gender;
        this.age = age;
        this.score = score;
    }

    public Character getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getScore() {
        return score;
    }
}