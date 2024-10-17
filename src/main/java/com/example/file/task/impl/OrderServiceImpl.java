package com.example.file.task.impl;

import com.example.file.task.dto.OrderDto;
import com.example.file.task.entity.Order;
import com.example.file.task.filter.OrderFilter;
import com.example.file.task.controller.mapper.OrderMapper;
import com.example.file.task.repository.OrderRepository;
import com.example.file.task.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public ResponseEntity<?> getOrderById(Integer id) {
        Optional<Order> optional = this.orderRepository.findById(id);
        if (optional.isPresent()) {
            Order order = optional.get();
            return ResponseEntity.ok(orderMapper.toDto(order));
        }
        return ResponseEntity.ok("Order not found");
    }

    @Override
    public ResponseEntity<?> getOrderByUserId(Integer userId) {
        List<Order> list = this.orderRepository.findByUserId(userId);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(this.orderMapper.toDtoList(list));
        }
        return ResponseEntity.ok("list is empty");
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        Optional<Order> optional = this.orderRepository.findById(id);
        if (optional.isPresent()) {
            Order order = optional.get();
            this.orderRepository.delete(order);
            return ResponseEntity.ok("Order deleted successfully");
        }
        return ResponseEntity.ok("Order not found");
    }

    @Override
    public ResponseEntity<?> deleteByUserId(Integer id) {
        List<Order> list = this.orderRepository.findByUserId(id);
        if (!list.isEmpty()) {
            this.orderRepository.deleteAll(list);
            return ResponseEntity.ok("Order deleted successfully");
        }
        return ResponseEntity.ok("List is empty");
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Order> list = this.orderRepository.findAll();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(this.orderMapper.toDtoList(list));
        }
        return ResponseEntity.ok("list is empty");
    }

    @Override
    public ResponseEntity<?> getAll(OrderFilter filter) {
        List<Order> all = this.orderRepository.findAll(filter);
        if (!all.isEmpty()) {
            return ResponseEntity.ok(this.orderMapper.toDtoList(all));
        }
        return ResponseEntity.ok("list is empty");
    }

    @Override
    public ResponseEntity<?> getAllPage(Pageable pageable) {
        List<Order> all = this.orderRepository.findAll();
        if (!all.isEmpty()) {
            List<OrderDto> list = all.stream().map(order -> orderMapper.toDto(order)).toList();
            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min(start + pageable.getPageSize(), list.size());

            List<OrderDto> output = list.subList(start, end);

            return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(output, pageable, list.size()));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("List is empty");
    }
}
