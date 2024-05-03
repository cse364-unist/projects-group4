package com.example.db.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.db.model.Summary;
import com.example.db.repository.SummaryRepository;
import com.example.db.controller.SummaryNotFoundException;

@RestController
class SummaryController {
    private final SummaryRepository repository;

    SummaryController(SummaryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/summaries")
    List<Summary> all() {
        return repository.findAll();
    }

    @PostMapping("/summaries")
    Summary newSummary(@RequestBody Summary newSummary) {
        // GPT part
        // import ratings DB
        // new timestamp
        return repository.save(newSummary);
    }

    @GetMapping("/summaries/{id}")
    Summary one(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new SummaryNotFoundException(id));
    }

    @PutMapping("/summaries/{id}")
    Summary replaceSummary(@RequestBody Summary newSummary, @PathVariable String id) {
        return repository.findById(id)
        .map(summary -> {
            summary.setMovieId(newSummary.getMovieId());
            summary.setTimestamp(newSummary.getTimestamp());
            summary.setSummaryText(newSummary.getSummaryText());
            return repository.save(summary);
        })
        .orElseGet(() -> {
            newSummary.setId(id);
            return repository.save(newSummary);
        });
    }

    @DeleteMapping("/summaries/{id}")
    void deleteSummary(@PathVariable String id) {
        repository.deleteById(id);
    }
}