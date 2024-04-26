package com.example.db.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.db.model.User;
import com.example.db.repository.UserRepository;
import com.example.db.controller.UserNotFoundException;

@RestController
class UserController {
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable String id) {
        return repository.findById(id)
        .map(user -> {
            user.setGender(newUser.getGender());
            user.setAge(newUser.getAge());
            user.setOccupation(newUser.getOccupation());
            user.setZipCode(newUser.getZipCode());
            return repository.save(user);
        })
        .orElseGet(() -> {
            newUser.setId(id);
            return repository.save(newUser);
        });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable String id) {
        repository.deleteById(id);
    }
}