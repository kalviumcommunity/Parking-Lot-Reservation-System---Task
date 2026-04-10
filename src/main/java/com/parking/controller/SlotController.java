package com.parking.controller;

import com.parking.dto.SlotRequest;
import com.parking.entity.Slot;
import com.parking.service.SlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotController {
    private final SlotService slotService;

    @PostMapping
    public ResponseEntity<Slot> createSlot(@Valid @RequestBody SlotRequest request) {
        return ResponseEntity.ok(slotService.createSlot(request));
    }
}
