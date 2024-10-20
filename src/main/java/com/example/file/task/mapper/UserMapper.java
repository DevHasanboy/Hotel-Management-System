package com.example.file.task.mapper;

import com.example.file.task.dto.UserDto;
import com.example.file.task.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .age(user.getAge())
                .email(user.getEmail())
                .role(user.getRole())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public List<UserDto> dtoList(List<User> users) {
        if (!users.isEmpty()) {
            return users.stream().map(this::toUserDto).toList();
        }
        return new ArrayList<>();
    }
}
