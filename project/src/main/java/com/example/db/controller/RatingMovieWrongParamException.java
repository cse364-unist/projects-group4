package com.example.db.controller;

class RatingMovieWrongParamException extends RuntimeException {
    RatingMovieWrongParamException() {
        super("Wrong rating score search parameter, it must be between 1 and 5.");
    }
}