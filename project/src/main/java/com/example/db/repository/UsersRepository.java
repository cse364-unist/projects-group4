package com.example.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.db.model.UsersModel;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersModel, Integer>{
    Optional<UsersModel> findByLoginAndPassword(String login, String password);
    Optional<UsersModel> findFirstByLogin(String login);

}
