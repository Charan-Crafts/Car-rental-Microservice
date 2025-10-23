package com.growandshine.user_service.Controller;

import com.growandshine.user_service.DTO.RegisterDTO;
import com.growandshine.user_service.DTO.RequestDTO;
import com.growandshine.user_service.Service.AuthService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        return authService.registerUser(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RequestDTO requestDTO){

        return authService.userLogin(requestDTO);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String tokenHeader){

        return authService.validateToken(tokenHeader);
    }
}
