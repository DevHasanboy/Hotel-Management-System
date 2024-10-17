package com.example.file.task.schedule;

import com.example.file.task.entity.Order;
import com.example.file.task.entity.Room;
import com.example.file.task.repository.OrderRepository;
import com.example.file.task.repository.RoomRepository;
import com.example.file.task.roles.RoomState;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@RequiredArgsConstructor
public class HotelSchedule {
    private final OrderRepository orderRepository;
    private final RoomRepository roomRepository;


    // check every 5 seconds
    @Scheduled(cron = "*/5 * * * * *")
    public void checkRoomActiveOrInactive() {
        final List<Order> all = this.orderRepository.findAll();
        for (Order order : all) {
            if (order.getBeginDate().isAfter(LocalDate.now()) && order.getEndDate().isBefore(LocalDate.now())) {
                Optional<Room> roomOptional = this.roomRepository.findById(order.getRoomId());
                if (roomOptional.isPresent()) {
                    Room room = roomOptional.get();
                    room.setState(RoomState.EMPTY);
                    this.roomRepository.save(room);
                }
            }
        }
    }
}
