package com.example.db.controller;

class RatingNotFoundException extends RuntimeException {
    RatingNotFoundException(String id) {
        super("Could not find rating " + id);
    }
}