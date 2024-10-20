package com.example.file.task.service;

import com.example.file.task.dto.ApiResponse;
import com.example.file.task.dto.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public interface PaymentService {
    ApiResponse create(PaymentDto.CreatePayment dto);

    ApiResponse get(Integer id);

    ApiResponse delete(Integer id);

    ApiResponse update(PaymentDto.CreatePayment dto, Integer id);

    ApiResponse getAll();
}
