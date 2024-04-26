package com.example.db.dal;

import java.util.List;

import com.example.db.model.User;

public interface UserDAL {

	List<User> getAllUsers();

	User getUserById(String id);

	User addNewUser(User user);
}