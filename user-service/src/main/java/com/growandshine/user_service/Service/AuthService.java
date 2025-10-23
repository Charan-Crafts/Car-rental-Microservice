package com.growandshine.user_service.Service;

import com.growandshine.user_service.Config.JwtUtils;
import com.growandshine.user_service.DTO.RegisterDTO;
import com.growandshine.user_service.DTO.RequestDTO;
import com.growandshine.user_service.Model.User;
import com.growandshine.user_service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<String> registerUser(RegisterDTO registerDTO) {

        //Check user

        User isUser = userRepository.findByEmail(registerDTO.getEmail()).orElse(null);

        if(isUser != null){

            return new ResponseEntity<>("User already exists with this email", HttpStatus.NOT_FOUND);
        }

        //Create new User

        User newUser = new User();
        newUser.setUserName(registerDTO.getUserName());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.setEmail(registerDTO.getEmail());
        newUser.setRole(registerDTO.getRole()!="ADMIN" || registerDTO.getRole()=="" ?"ROLE":"ADMIN");

        userRepository.save(newUser);

        return new ResponseEntity<>("User created",HttpStatus.CREATED);
    }

    public ResponseEntity<String> userLogin(RequestDTO requestDTO) {

        //

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getEmail(),requestDTO.getPassword()));

        if(authentication.isAuthenticated()){

            String token = jwtUtils.generateToken(requestDTO.getEmail());

            return new ResponseEntity<>(token,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Invalid credentials",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> validateToken(String tokenHeader) {

        // Remove "Bearer " prefix if present
        String token = tokenHeader.startsWith("Bearer ") ? tokenHeader.substring(7) : tokenHeader;

        boolean isValid = jwtUtils.validateToken(token);

        if (isValid) {
            String email = jwtUtils.extractEmail(token); // extractUsername instead of extractEmail
            return ResponseEntity.ok("Token is valid for user: " + email);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

}
