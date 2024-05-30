package com.gadgetnest.app.service;

import com.gadgetnest.app.entity.User;

import java.util.Optional;

public interface UserService {

    public Optional<User> getUserByEmail(String email);

    public User registerUser(User user);

    public User loginUser(String email, String password);
}
