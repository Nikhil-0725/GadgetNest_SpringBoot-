package com.gadgetnest.app.service.impl;

import com.gadgetnest.app.entity.User;
import com.gadgetnest.app.exception.DuplicateEmailException;
import com.gadgetnest.app.exception.InvalidAnswerException;
import com.gadgetnest.app.exception.InvalidCredentialsException;
import com.gadgetnest.app.exception.ResourceNotFoundException;
import com.gadgetnest.app.repository.UserRepository;
import com.gadgetnest.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        try{
//            user.setPassword(passwordEncoder.encode(user.getPassword()))
            return userRepository.save(user);
        } catch(DataIntegrityViolationException e){
            throw new DuplicateEmailException("Email already registered, You can login.");
        }
    }

    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this email :: " + email));

//        if (passwordEncoder.matches(password, user.getPassword()))
        if(password.equals(user.getPassword())){
            return user;
        } else {
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    @Override
    public User resetPassword(String email, String answer, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this email : " + email));

        if(!answer.equals(user.getAnswer())){
            throw new InvalidAnswerException("Invalid Security Answer");
        }

//        user.setPassword(passwordEncoder.encode(user.getPassword()))
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

}
