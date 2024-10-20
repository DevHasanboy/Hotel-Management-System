package com.example.file.task.controller;

import com.example.file.task.dto.ApiResponse;
import com.example.file.task.dto.PaymentDto;
import com.example.file.task.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    ApiResponse create(@RequestBody PaymentDto.CreatePayment dto) {
        return this.paymentService.create(dto);
    }

    @GetMapping("/get")
    ApiResponse get(@RequestParam("id") Integer id) {
        return this.paymentService.get(id);
    }

    @DeleteMapping("/delete")
    ApiResponse delete(@RequestParam("id") Integer id) {
        return this.paymentService.delete(id);
    }

    @PutMapping("/update")
    ApiResponse update(@RequestBody PaymentDto.CreatePayment dto,
                       @RequestParam("/id") Integer id) {
        return this.paymentService.update(dto, id);
    }

    @GetMapping("/getAll")
    ApiResponse getAll() {
        return this.paymentService.getAll();
    }
}
