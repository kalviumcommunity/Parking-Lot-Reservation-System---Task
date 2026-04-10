package com.parking.service;

import com.parking.dto.SlotRequest;
import com.parking.entity.Floor;
import com.parking.entity.Slot;
import com.parking.exception.ResourceNotFoundException;
import com.parking.repository.FloorRepository;
import com.parking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotService {
    private final SlotRepository slotRepository;
    private final FloorRepository floorRepository;

    public Slot createSlot(SlotRequest request) {
        Floor floor = floorRepository.findById(request.getFloorId())
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found with ID: " + request.getFloorId()));
        
        Slot slot = Slot.builder()
                .slotNumber(request.getSlotNumber())
                .floor(floor)
                .isAvailable(true)
                .build();
        return slotRepository.save(slot);
    }
}
