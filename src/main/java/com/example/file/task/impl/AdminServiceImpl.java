package com.example.file.task.impl;

import com.example.file.task.dto.UserDto;
import com.example.file.task.entity.Order;
import com.example.file.task.entity.User;
import com.example.file.task.mapper.UserMapper;
import com.example.file.task.repository.OrderRepository;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.roles.UserRole;
import com.example.file.task.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<?> updateAdmin(Integer id) {
        Optional<User> optional = this.userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setRole(UserRole.ADMIN);
            this.userRepository.save(user);
            return ResponseEntity.ok("updated successfully");
        }
        return ResponseEntity.ok("User not found");
    }

    @Override
    public ResponseEntity<?> deleteAdmin(Integer id) {
        Optional<User> optional = this.userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            this.userRepository.delete(user);
            return ResponseEntity.ok("deleted successfully");
        }
        return ResponseEntity.ok("User not found");
    }

    @Override
    public ResponseEntity<?> findAllAdmin() {
        List<User> allAdmin = this.userRepository.findAllAdmin();
        if (!allAdmin.isEmpty()) {
            return ResponseEntity.ok(allAdmin);
        }
        return ResponseEntity.ok("List is empty");
    }

    @Override
    public ResponseEntity<?> deleteUserById(Integer id) {
        Optional<User> optional = this.userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            List<Order> list = this.orderRepository.findByUserId(user.getId());
            if (!list.isEmpty()) {
                this.orderRepository.deleteByUserId(user.getId());
                this.userRepository.delete(user);
                return ResponseEntity.ok("deleted successfully");
            }
        }
        return ResponseEntity.ok("User not found");
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<User> list = this.userRepository.findAll();
        if (!list.isEmpty()) {
            List<UserDto> dtoList = this.userMapper.dtoList(list);
            return ResponseEntity.ok(dtoList);
        }
        return ResponseEntity.ok("List is empty");
    }
}
