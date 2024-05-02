package com.example.db.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.db.model.Achievement;
import com.example.db.model.AchievementId;
import com.example.db.repository.AchievementRepository;
import com.example.db.controller.AchievementNotFoundException;

@RestController
class AchievementController {
    private final AchievementRepository repository;

    AchievementController(AchievementRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/achievements")
    List<Achievement> all() {
        return repository.findAll();
    }

    @GetMapping("/achievements/{userId}/{achievement}")
    Achievement one(
        @PathVariable String userId,
        @PathVariable String achievement) {
        AchievementId id = new AchievementId(userId, achievement); 
        return repository.findById(id)
            .orElseThrow(() -> new AchievementNotFoundException());
    }

    @PostMapping("/achievements")
    Achievement newAchievement(@RequestBody Achievement newAchievement) {
        return repository.save(newAchievement);
    }

    @PutMapping("/achievements/{userId}/{achievement}")
    Achievement replaceAchievement(
        @RequestBody Achievement newAchievement,
        @PathVariable String userId,
        @PathVariable String achievement) {
        AchievementId id = new AchievementId(userId, achievement);
        return repository.findById(id)
        .map(oldAchievement -> {
            oldAchievement.update(newAchievement);
            return repository.save(oldAchievement);
        })
        .orElseGet(() -> {
            newAchievement.setId(id);
            return repository.save(newAchievement);
        });
    }

    @DeleteMapping("/achievements/{userId}/{achievement}")
    void deleteAchievement(
        @PathVariable String userId,
        @PathVariable String achievement) {
        AchievementId id = new AchievementId(userId, achievement); 
        repository.deleteById(id);
    }
}