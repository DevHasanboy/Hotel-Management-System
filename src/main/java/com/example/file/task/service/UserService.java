package com.example.file.task.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    ResponseEntity<?> chooseHotel(Integer hotelId, Integer userId);

    ResponseEntity<?> chooseRoom(Integer roomId, Integer userId, Integer numberOfPeople);
}
