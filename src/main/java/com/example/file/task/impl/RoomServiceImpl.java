package com.example.file.task.impl;

import com.example.file.task.dto.RoomDto;
import com.example.file.task.entity.Hotel;
import com.example.file.task.entity.Room;
import com.example.file.task.filter.RoomFilter;
import com.example.file.task.controller.mapper.RoomMapper;
import com.example.file.task.repository.HotelRepository;
import com.example.file.task.repository.RoomRepository;
import com.example.file.task.service.RoomService;
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
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;

    @Override
    public ResponseEntity<?> create(RoomDto.CreateDto dto) {
        if (this.hotelRepository.findById(dto.getHotelId()).isPresent()) {
            Room entity = this.roomMapper.toEntity(dto);
            this.roomRepository.save(entity);
            return ResponseEntity.ok(this.roomMapper.toDto(entity));
        }
        return ResponseEntity.ok("Hotel does not exist");
    }

    @Override
    public ResponseEntity<?> get(Integer id) {
        Optional<Room> roomOptional = this.roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            return ResponseEntity.ok(this.roomMapper.toDto(room));
        }
        return ResponseEntity.ok("Room does not exist");
    }

    @Override
    public ResponseEntity<?> update(RoomDto.CreateDto dto, Integer id) {
        Optional<Hotel> optional = this.hotelRepository.findById(dto.getHotelId());
        if (optional.isPresent()) {
            Optional<Room> roomOptional = this.roomRepository.findById(id);
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                this.roomMapper.update(dto, room);
                this.roomRepository.save(room);
                return ResponseEntity.ok(this.roomMapper.toDto(room));
            }
            return ResponseEntity.ok("Room does not exist");
        }
        return ResponseEntity.ok("Hotel does not exist");
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        Optional<Room> roomOptional = this.roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            this.roomRepository.delete(room);
            return ResponseEntity.ok("Room deleted successfully");
        }
        return ResponseEntity.ok("Room does not exist");
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Room> roomList = this.roomRepository.findAll();
        if (!roomList.isEmpty())
            return ResponseEntity.ok(this.roomMapper.toDtoList(roomList));
        return ResponseEntity.ok("List is empty");
    }

    @Override
    public ResponseEntity<?> getAll(RoomFilter filter) {
        List<Room> all = this.roomRepository.findAll(filter);
        if (!all.isEmpty())
            return ResponseEntity.ok(this.roomMapper.toDtoList(all));
        return ResponseEntity.ok("List is empty");
    }

    @Override
    public ResponseEntity<?> getAllPage(Pageable pageable) {
        List<Room> all = this.roomRepository.findAll();

        if (!all.isEmpty()) {
            List<RoomDto> list = all.stream().map(room -> roomMapper.toDto(room)).toList();
            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min(start + pageable.getPageSize(), list.size());
            List<RoomDto> output = list.subList(start, end);
            return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(output, pageable, list.size()));

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("List is empty");
    }
}
