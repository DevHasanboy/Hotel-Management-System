package com.example.file.task.controller;

import com.example.file.task.dto.RoomDto;
import com.example.file.task.filter.RoomFilter;
import com.example.file.task.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RoomDto.CreateDto dto) {
        return ResponseEntity.ok(this.roomService.create(dto));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.roomService.get(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody RoomDto.CreateDto dto,
                                    @RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.roomService.update(dto, id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.roomService.delete(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(this.roomService.getAll());
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll(@RequestParam(required = false, name = "row-number") int rowNumber) {
        var filter = new RoomFilter();
        filter.setRowNumber(rowNumber);
        ResponseEntity response = this.roomService.getAll(filter);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get_all_page")
    public ResponseEntity<?> getAllPage(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseEntity<?> pag = this.roomService.getAllPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pag);

    }
}



