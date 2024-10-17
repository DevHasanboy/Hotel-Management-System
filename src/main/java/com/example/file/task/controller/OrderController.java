package com.example.file.task.controller;

import com.example.file.task.filter.OrderFilter;
import com.example.file.task.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/getOrderById")
    public ResponseEntity<?> getOrderById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/getOrderByUserId")
    public ResponseEntity<?> getOrderByUserId(@RequestParam("user-id") Integer userId) {
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(orderService.deleteById(id));
    }

    @DeleteMapping("/deleteByUserId")
    public ResponseEntity<?> deleteByUserId(@RequestParam("user-id") Integer userId) {
        return ResponseEntity.ok(orderService.deleteByUserId(userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll(@RequestParam(required = false, name = "numberOfPeople") int numberOfPeople) {
        var filter = new OrderFilter();
        filter.setNumberOfPeople(numberOfPeople);
        ResponseEntity response = this.orderService.getAll(filter);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get_all_page")
    public ResponseEntity<?> getAllPage(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseEntity<?> pag = this.orderService.getAllPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pag);

    }
}
