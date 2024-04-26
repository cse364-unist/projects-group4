package com.example.db.repository;

import java.util.List;

public interface RatingRepositoryCustom {
    List<String> findMoviesWithRatingAtLeast(int minRating);
}