package com.parking.service;

import com.parking.dto.AvailabilityResponse;
import com.parking.entity.Slot;
import com.parking.repository.ReservationRepository;
import com.parking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final SlotRepository slotRepository;
    private final ReservationRepository reservationRepository;

    public List<AvailabilityResponse> getAvailableSlots(LocalDateTime start, LocalDateTime end) {
        // Find all slots
        List<Slot> allSlots = slotRepository.findAll();
        
        // Find slots that are reserved in the given time range
        List<Slot> reservedSlots = reservationRepository.findReservedSlotsInTimeRange(start, end);
        Set<Long> reservedSlotIds = reservedSlots.stream()
                .map(Slot::getId)
                .collect(Collectors.toSet());

        // Filter out reserved slots
        return allSlots.stream()
                .filter(slot -> !reservedSlotIds.contains(slot.getId()))
                .map(slot -> AvailabilityResponse.builder()
                        .id(slot.getId())
                        .slotNumber(slot.getSlotNumber())
                        .floorName(slot.getFloor().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
