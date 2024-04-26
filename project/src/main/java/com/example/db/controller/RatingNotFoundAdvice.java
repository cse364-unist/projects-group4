package com.example.db.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.db.controller.RatingNotFoundException;

@ControllerAdvice
class RatingNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(RatingNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String ratingNotFoundHandler(RatingNotFoundException ex) {
    return ex.getMessage();
  }
}