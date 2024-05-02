package com.example.db.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.db.controller.AchievementNotFoundException;

@ControllerAdvice
class AchievementNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(AchievementNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String achievementNotFoundhandler(AchievementNotFoundException ex) {
    return ex.getMessage();
  }
}