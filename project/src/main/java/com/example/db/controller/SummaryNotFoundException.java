package com.example.db.controller;

class SummaryNotFoundException extends RuntimeException {
    SummaryNotFoundException(String id) {
        super("Could not find summary " + id);
    }
}