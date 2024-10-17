package com.example.file.task.service;


import com.example.file.task.dto.RoomDto;
import com.example.file.task.filter.RoomFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface RoomService {

    ResponseEntity<?> create(RoomDto.CreateDto dto);

    ResponseEntity<?> get(Integer id);

    ResponseEntity<?> update(RoomDto.CreateDto dto, Integer id);

    ResponseEntity<?> delete(Integer id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> getAll(RoomFilter filter);

    ResponseEntity<?> getAllPage(Pageable pageable);
}
