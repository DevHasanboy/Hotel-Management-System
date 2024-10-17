package com.example.file.task.controller;

import com.example.file.task.dto.HotelDto;
import com.example.file.task.filter.HotelFilter;
import com.example.file.task.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createHotel(@RequestBody HotelDto.CreateHotel dto) {
        return ResponseEntity.ok(this.hotelService.createHotel(dto));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.hotelService.get(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> update(@RequestBody HotelDto.CreateHotel dto,
                                    @RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.hotelService.update(dto, id));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.hotelService.delete(id));
    }

    @GetMapping("/getAllOwnHotel")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAllOwnHotel(@RequestParam("id") Integer ownerId) {
        return ResponseEntity.ok(this.hotelService.getAllHotel(ownerId));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','USER','SUPER_ADMIN')")
    public ResponseEntity<?> getAllHotel() {
        return ResponseEntity.ok(this.hotelService.getAllHotel());
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','USER','SUPER_ADMIN')")
    public ResponseEntity<?> findAll(@RequestParam(required = false, name = "name") String name,
                                     @RequestParam(required = false, name = "address") String address) {
        var filter = new HotelFilter();
        filter.setName(name);
        filter.setAddress(address);
        ResponseEntity response = this.hotelService.findAll(filter);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get_all_page")
    public ResponseEntity<?> getAllPage(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseEntity<?> pag = this.hotelService.getAllPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pag);

    }
}
