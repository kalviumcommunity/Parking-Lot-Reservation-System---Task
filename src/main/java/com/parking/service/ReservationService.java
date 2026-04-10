package com.parking.service;

import com.parking.dto.ReservationRequest;
import com.parking.dto.ReservationResponse;
import com.parking.entity.Reservation;
import com.parking.entity.ReservationStatus;
import com.parking.entity.Slot;
import com.parking.exception.InvalidTimeRangeException;
import com.parking.exception.OverlappingReservationException;
import com.parking.exception.ResourceNotFoundException;
import com.parking.repository.ReservationRepository;
import com.parking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SlotRepository slotRepository;
    private final FeeCalculator feeCalculator;

    public ReservationResponse createReservation(ReservationRequest request) {
        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with ID: " + request.getSlotId()));

        validateTimeRange(request.getStartTime(), request.getEndTime());
        checkOverlap(slot, request.getStartTime(), request.getEndTime());

        Reservation reservation = Reservation.builder()
                .slot(slot)
                .vehicleNumber(request.getVehicleNumber())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .totalFee(feeCalculator.calculateFee(request.getStartTime(), request.getEndTime()))
                .status(ReservationStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
        return mapToResponse(reservation);
    }

    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new InvalidTimeRangeException("Start time must be before end time");
        }
        if (Duration.between(start, end).toHours() > 24) {
            throw new InvalidTimeRangeException("Reservation duration cannot exceed 24 hours");
        }
    }

    private void checkOverlap(Slot slot, LocalDateTime start, LocalDateTime end) {
        List<Reservation> overlapping = reservationRepository.findOverlappingReservations(slot, start, end);
        if (!overlapping.isEmpty()) {
            throw new OverlappingReservationException("Slot is already reserved for the selected time range");
        }
    }

    private ReservationResponse mapToResponse(Reservation r) {
        return ReservationResponse.builder()
                .id(r.getId())
                .slotNumber(r.getSlot().getSlotNumber())
                .floorName(r.getSlot().getFloor().getName())
                .vehicleNumber(r.getVehicleNumber())
                .startTime(r.getStartTime())
                .endTime(r.getEndTime())
                .totalFee(r.getTotalFee())
                .status(r.getStatus())
                .build();
    }
}
