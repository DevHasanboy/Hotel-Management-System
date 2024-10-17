package com.example.file.task.impl;

import com.example.file.task.entity.Hotel;
import com.example.file.task.entity.Order;
import com.example.file.task.entity.Room;
import com.example.file.task.entity.User;
import com.example.file.task.repository.HotelRepository;
import com.example.file.task.repository.OrderRepository;
import com.example.file.task.repository.RoomRepository;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.roles.RoomState;
import com.example.file.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<?> chooseHotel(Integer hotelId, Integer userId) {
        Optional<User> optional = this.userRepository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            Optional<Hotel> op = this.hotelRepository.findById(hotelId);
            if (op.isPresent()) {
                Hotel hotel = op.get();
                user.setHotel(hotel);
                this.userRepository.save(user);
            }
            return ResponseEntity.ok("hotel is not found");
        }
        return ResponseEntity.ok("user is not found");
    }

    @Override
    public ResponseEntity<?> chooseRoom(Integer roomId, Integer userId, Integer numberOfPeople) {
        Optional<User> optional = this.userRepository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            Optional<Room> roomOptional = this.roomRepository.findActiveEmptyRoomById(roomId);
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                if (user.getHotel().getId().equals(room.getHotelId())) {
                    if (room.getNumberOfPeople() >= numberOfPeople) {
                        room.setEnable(true);
                        room.setState(RoomState.FULL);
                        this.roomRepository.save(room);
                        Order order = Order.builder()
                                .beginDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(5))
                                .numberOfPeople(numberOfPeople)
                                .roomId(room.getId())
                                .userId(user.getId())
                                .build();
                        this.orderRepository.save(order);
                    }
                    return ResponseEntity.ok("Choose another bigger room");
                }
                return ResponseEntity.ok("Something went wrong");
            }
            return ResponseEntity.ok("Room is not found");
        }
        return ResponseEntity.ok("User is not found");
    }
}
