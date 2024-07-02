package com.gadgetnest.app.service.impl;

import com.gadgetnest.app.entity.User;
import com.gadgetnest.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Load User from

        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return user;
    }
}
