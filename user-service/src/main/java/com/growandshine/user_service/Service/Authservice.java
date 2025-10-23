package com.growandshine.user_service.Service;

import com.growandshine.user_service.Config.JwtService;
import com.growandshine.user_service.DTO.RegisterDTO;
import com.growandshine.user_service.DTO.UserLoginDTO;
import com.growandshine.user_service.DTO.UserResponse;
import com.growandshine.user_service.Model.User;
import com.growandshine.user_service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Authservice {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<String> userRegistration(RegisterDTO registerDTO) {

        //Check the user

        User isUser = userRepository.findByUserName(registerDTO.getUserName()).orElse(null);

        if(isUser!=null){
            return new ResponseEntity<>("User is already exists with this Email", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setUserName(registerDTO.getUserName());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUser.setEmail(registerDTO.getEmail());

        userRepository.save(newUser);

        return new ResponseEntity<>("User is registered",HttpStatus.CREATED);
    }

    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<User> userList = userRepository.findAll();

        //conver the user into the userresponse dto

        List<UserResponse> response = userList.stream()
                .map(this::convertUserIntoDTO)
                .toList();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public UserResponse convertUserIntoDTO(User user){
        return UserResponse.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public ResponseEntity<String> login(UserLoginDTO userLoginDTO) {

        String token = jwtService.generateToken(userLoginDTO.getEmail());

        return new ResponseEntity<>(token,HttpStatus.OK);

    }
}
