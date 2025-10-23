package com.growandshine.booking_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @GetMapping
    public ResponseEntity<String> health(){
        return new ResponseEntity<>("Booking service is running at port ", HttpStatus.OK);
    }
}
