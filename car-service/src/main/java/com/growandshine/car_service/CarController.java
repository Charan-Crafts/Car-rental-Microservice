package com.growandshine.car_service;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @GetMapping
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("Car service is running", HttpStatus.OK);
    }
}
