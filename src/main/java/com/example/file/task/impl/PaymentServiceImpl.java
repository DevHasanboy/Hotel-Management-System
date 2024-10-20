package com.example.file.task.impl;

import com.example.file.task.dto.ApiResponse;
import com.example.file.task.dto.PaymentDto;
import com.example.file.task.entity.Payment;
import com.example.file.task.repository.PaymentRepository;
import com.example.file.task.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public ApiResponse create(PaymentDto.CreatePayment dto) {
        try {
            Payment payment = new Payment();
            payment.setAmount(dto.getAmount());
            payment.setPaymentType(dto.getPaymentType());
            payment.setCreatedAt(LocalDateTime.now());

            paymentRepository.save(payment);

            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Payment created")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .message("Failed to create payment")
                    .build();
        }
    }

    @Override
    public ApiResponse get(Integer id) {
        try {
            Optional<Payment> paymentOpt = paymentRepository.findById(id);

            if (paymentOpt.isPresent()) {
                return ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .data(paymentOpt.get())
                        .build();
            } else {
                return ApiResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Payment not found")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .message("Failed to create payment")
                    .build();
        }
    }

    @Override
    public ApiResponse delete(Integer id) {
        try {
            if (paymentRepository.existsById(id)) {
                paymentRepository.deleteById(id);
                return ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Payment deleted")
                        .build();
            } else {
                return ApiResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Payment not found")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .message("Failed to create payment")
                    .build();
        }
    }

    @Override
    public ApiResponse update(PaymentDto.CreatePayment dto, Integer id) {
        try {
            Optional<Payment> paymentOpt = paymentRepository.findById(id);

            if (paymentOpt.isPresent()) {
                Payment payment = paymentOpt.get();
                payment.setAmount(dto.getAmount());
                payment.setPaymentType(dto.getPaymentType());

                paymentRepository.save(payment);
                return ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Payment updated")
                        .build();
            } else {
                return ApiResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("Payment not found")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .message("Failed to create payment")
                    .build();
        }
    }

    @Override
    public ApiResponse getAll() {
        try {
            List<Payment> payments = paymentRepository.findAll();
            if (!payments.isEmpty()) {
                return ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .data(payments)
                        .build();
            }
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .data(new ArrayList<>())
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .message("Failed to create payment")
                    .build();
        }
    }
}

