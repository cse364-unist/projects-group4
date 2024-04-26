package com.example.db.model;

import org.springframework.data.annotation.Id;

public class User {
    @Id private String id;
    private Character gender;
    private Integer age;
    private Integer occupation;
    private String zipCode;

    public User() {}

    public User(String id, Character gender, Integer age, Integer occupation, String zipCode) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}