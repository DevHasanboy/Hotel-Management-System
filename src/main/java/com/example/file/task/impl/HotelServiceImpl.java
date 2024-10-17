package com.example.file.task.impl;

import com.example.file.task.dto.HotelDto;
import com.example.file.task.entity.Hotel;
import com.example.file.task.filter.HotelFilter;
import com.example.file.task.controller.mapper.HotelMapper;
import com.example.file.task.repository.HotelRepository;
import com.example.file.task.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;


    @Override
    public ResponseEntity<?> createHotel(HotelDto.CreateHotel hotel) {
        Hotel entity = this.hotelMapper.toEntity(hotel);
        this.hotelRepository.save(entity);
        return ResponseEntity.ok(this.hotelMapper.toDto(entity));
    }

    @Override
    public ResponseEntity<?> get(Integer id) {
        Optional<Hotel> optional = this.hotelRepository.findById(id);
        if (optional.isPresent()) {
            Hotel hotel = optional.get();
            return ResponseEntity.ok(this.hotelMapper.toDto(hotel));
        }
        return ResponseEntity.ok("hotel not found");
    }

    @Override
    public ResponseEntity<?> update(HotelDto.CreateHotel hotel, Integer id) {
        Optional<Hotel> optional = this.hotelRepository.findById(id);
        if (optional.isPresent()) {
            Hotel entity = optional.get();
            this.hotelMapper.update(hotel, entity);
            this.hotelRepository.save(entity);
            return ResponseEntity.ok(this.hotelMapper.toDto(entity));
        }
        return ResponseEntity.ok("hotel not found");
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        Optional<Hotel> optional = this.hotelRepository.findById(id);
        if (optional.isPresent()) {
            this.hotelRepository.deleteById(id);
            return ResponseEntity.ok("hotel deleted successfully");
        }
        return ResponseEntity.ok("hotel not found");
    }

    @Override
    public ResponseEntity<?> getAllHotel(Integer ownerId) {
        List<Hotel> list = this.hotelRepository.findByOwnerId(ownerId);
        if (!list.isEmpty())
            return ResponseEntity.ok(this.hotelMapper.getAll(list));
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    public ResponseEntity<?> getAllHotel() {
        List<Hotel> list = this.hotelRepository.findAll();
        if (!list.isEmpty())
            return ResponseEntity.ok(this.hotelMapper.getAll(list));
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    public ResponseEntity<?> findAll(HotelFilter filter) {
        List<Hotel> all = this.hotelRepository.findAll(filter);
        if (!all.isEmpty())
            return ResponseEntity.ok(this.hotelMapper.getAll(all));
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Override
    public ResponseEntity<?> getAllPage(Pageable pageable) {
        List<Hotel> all = this.hotelRepository.findAll();
        if (!all.isEmpty()) {
            List<HotelDto> list = all.stream().map(hotel -> hotelMapper.toDto(hotel)).toList();

            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min(start + pageable.getPageSize(), list.size());

            List<HotelDto> output = list.subList(start, end);

            return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(output, pageable, list.size()));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("List is empty");

    }
}
