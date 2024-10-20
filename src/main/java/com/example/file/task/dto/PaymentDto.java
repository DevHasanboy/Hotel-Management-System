package com.example.file.task.dto;

import com.example.file.task.enums.PaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private Integer id;
    private LocalDateTime createdAt;
    private PaymentType paymentType;
    private Double amount;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreatePayment {
        private PaymentType paymentType;
        private Double amount;
    }
}
