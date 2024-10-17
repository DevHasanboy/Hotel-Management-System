package com.example.file.task.service;

import com.example.file.task.filter.OrderFilter;
import com.example.file.task.filter.RoomFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrderService {
    ResponseEntity<?> getOrderById(Integer id);

    ResponseEntity<?> getOrderByUserId(Integer userId);

    ResponseEntity<?> deleteById(Integer id);

    ResponseEntity<?> deleteByUserId(Integer id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> getAll(OrderFilter filter);

    ResponseEntity<?> getAllPage(Pageable pageable);
}
