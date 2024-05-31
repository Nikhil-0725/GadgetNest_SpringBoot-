package com.gadgetnest.app.controller;

import com.gadgetnest.app.dto.UserLoginDto;
import com.gadgetnest.app.dto.UserResetPasswordDto;
import com.gadgetnest.app.entity.User;
import com.gadgetnest.app.exception.InvalidAnswerException;
import com.gadgetnest.app.exception.InvalidCredentialsException;
import com.gadgetnest.app.exception.ResourceNotFoundException;
import com.gadgetnest.app.service.UserService;
import com.gadgetnest.app.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody User user, BindingResult result){

        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, result.getAllErrors().get(0).getDefaultMessage(), null));
        }

//        Optional<User> exisingUser = userService.getUserByEmail(user.getEmail());
//
//        if(exisingUser.isPresent()){
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ApiResponse<>(true, "Already Registered, Please Login", null));
//        }

        userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", user));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody UserLoginDto user){
        try{
            User u = userService.loginUser(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(new ApiResponse<>(true, "Login Successfull", u));
        } catch (InvalidCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody UserResetPasswordDto user){
        try{
            User u = userService.resetPassword(user.getEmail(), user.getAnswer(), user.getNewPassword());
            return ResponseEntity.ok(new ApiResponse<>(true, "Password Reset Successfull", u));
        } catch (InvalidAnswerException | ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }
}
