package com.gadgetnest.app.service;

import com.gadgetnest.app.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public Optional<User> getUserByEmail(String email);

    public User registerUser(User user);

    public User loginUser(String email, String password);

    public User resetPassword(String email, String answer, String newPassword);

    public List<User> getAllUsers();
}
