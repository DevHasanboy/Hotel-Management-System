package com.example.file.task.controller;

import com.example.file.task.dto.AuthRequest;
import com.example.file.task.dto.LoginRequest;
import com.example.file.task.entity.User;
import com.example.file.task.impl.MailSenderServiceImpl;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.roles.UserRole;
import com.example.file.task.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:1111")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final MailSenderServiceImpl mailSenderService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) throws Exception {
        if (this.userRepository.findByEmail(authRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("USERNAME IS ALREADY EXISTS");
        }
        User user = User.builder()
                .role(UserRole.USER)
                .email(authRequest.getEmail())
                .age(authRequest.getAge())
                .lastname(authRequest.getLastname())
                .firstname(authRequest.getFirstname())
                .phoneNumber(authRequest.getPhoneNumber())
                .build();
        String code = mailSenderService.sendCodeToMail(authRequest.getEmail());
        user.setCode(code);
        user.setPassword(passwordEncoder.encode(code));
        this.userRepository.save(user);
        return ResponseEntity.ok("CODE SEND SUCCESSFULLY");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest authRequest) throws Exception {
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new Exception("User not found"));
        if (user == null || !user.getCode().equals(authRequest.getCode())) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getCode())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(jwt);
    }

    @GetMapping("/getUsername")
    public ResponseEntity<?> getUsername(@RequestParam String token) {
        String username = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(username);
    }
}
