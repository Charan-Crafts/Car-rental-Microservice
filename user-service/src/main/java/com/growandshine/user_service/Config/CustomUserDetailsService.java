package com.growandshine.user_service.Config;

import com.growandshine.user_service.Model.User;
import com.growandshine.user_service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User isUser = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Invalid user credentials"));

        return new CustomUserDetails(isUser);
    }
}
