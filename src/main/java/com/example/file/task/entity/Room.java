package com.example.file.task.entity;

import com.example.file.task.enums.RoomState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "room")
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private String description;
    private Boolean enable = true;
    private Integer numberOfPeople;
    private Double price;
    private Integer hotelId;
    @Enumerated(EnumType.STRING)
    private RoomState status = RoomState.ACTIVE;
    @Enumerated(EnumType.STRING)
    private RoomState state = RoomState.EMPTY;
}
