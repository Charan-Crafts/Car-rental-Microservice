package com.growandshine.user_service.Controllers;

import com.growandshine.user_service.DTO.RegisterDTO;
import com.growandshine.user_service.DTO.UserLoginDTO;
import com.growandshine.user_service.DTO.UserResponse;
import com.growandshine.user_service.Model.User;
import com.growandshine.user_service.Service.Authservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private Authservice authservice;

    @GetMapping()
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("User service is running", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegistration(@RequestBody RegisterDTO registerDTO){

        return authservice.userRegistration(registerDTO);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){

        return authservice.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO){

        return authservice.login(userLoginDTO);
    }
}
