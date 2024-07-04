package com.gadgetnest.app.controller;

import com.gadgetnest.app.dto.UserLoginDto;
import com.gadgetnest.app.dto.UserResetPasswordDto;
import com.gadgetnest.app.entity.User;
import com.gadgetnest.app.security.JwtHelper;
import com.gadgetnest.app.service.UserService;
import com.gadgetnest.app.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody User user, BindingResult result){

        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, result.getAllErrors().get(0).getDefaultMessage(), null));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User user1 = userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", user1));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody UserLoginDto user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtHelper.generateToken(userDetails);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login Successfull", jwtToken));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody UserResetPasswordDto user){

        User u = userService.resetPassword(user.getEmail(), user.getAnswer(), user.getNewPassword());
        return ResponseEntity.ok(new ApiResponse<>(true, "Password Reset Successfull", u));

    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "User Fetched Successfully",users));
    }
}
