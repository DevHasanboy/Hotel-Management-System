package com.example.file.task.service;

import com.example.file.task.dto.HotelDto;
import com.example.file.task.filter.HotelFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface HotelService {

    ResponseEntity<?> createHotel(HotelDto.CreateHotel hotel);

    ResponseEntity<?> get(Integer id);

    ResponseEntity<?> update(HotelDto.CreateHotel hotel, Integer id);

    ResponseEntity<?> delete(Integer id);

    ResponseEntity<?> getAllHotel(Integer ownerId);

    ResponseEntity<?> getAllHotel();

    ResponseEntity<?> findAll(HotelFilter filter);

    ResponseEntity<?> getAllPage(Pageable pageable);
}
