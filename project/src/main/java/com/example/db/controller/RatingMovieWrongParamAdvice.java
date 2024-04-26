package com.example.db.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.db.controller.RatingMovieWrongParamException;

@ControllerAdvice
class RatingMovieWrongParamAdvice {

  @ResponseBody
  @ExceptionHandler(RatingMovieWrongParamException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String ratingNotFoundHandler(RatingMovieWrongParamException ex) {
    return ex.getMessage();
  }
}