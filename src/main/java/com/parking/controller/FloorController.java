package com.parking.controller;

import com.parking.dto.FloorRequest;
import com.parking.entity.Floor;
import com.parking.service.FloorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/floors")
@RequiredArgsConstructor
public class FloorController {
    private final FloorService floorService;

    @PostMapping
    public ResponseEntity<Floor> createFloor(@Valid @RequestBody FloorRequest request) {
        return ResponseEntity.ok(floorService.createFloor(request));
    }
}
