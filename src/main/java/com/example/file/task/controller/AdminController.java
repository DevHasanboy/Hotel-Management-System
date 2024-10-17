package com.example.file.task.controller;

import com.example.file.task.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/superAdmin")
@PreAuthorize(value = "hasRole('SUPER_ADMIN')")
public class AdminController {
    private final AdminService adminService;


    @PutMapping("/updateRoleToAdmin")
    public ResponseEntity<?> updateRoleToAdmin(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.adminService.updateAdmin(id));
    }

    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<?> deleteAdmin(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.adminService.deleteAdmin(id));
    }

    @GetMapping("/findAllAdmin")
    public ResponseEntity<?> findAllAdmin() {
        return ResponseEntity.ok(this.adminService.findAllAdmin());
    }

    @DeleteMapping("/deleteUserById")
    public ResponseEntity<?> deleteUserById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.adminService.deleteUserById(id));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(this.adminService.getAllUsers());
    }
}
