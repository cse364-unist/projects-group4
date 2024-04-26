package com.example.db.controller;

class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String id) {
        super("Could not find user " + id);
    }
}