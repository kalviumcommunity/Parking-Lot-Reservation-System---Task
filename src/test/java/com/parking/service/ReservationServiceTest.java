package com.parking.service;

import com.parking.dto.ReservationRequest;
import com.parking.entity.Floor;
import com.parking.entity.Reservation;
import com.parking.entity.ReservationStatus;
import com.parking.entity.Slot;
import com.parking.exception.InvalidTimeRangeException;
import com.parking.exception.OverlappingReservationException;
import com.parking.repository.ReservationRepository;
import com.parking.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private FeeCalculator feeCalculator;

    @InjectMocks
    private ReservationService reservationService;

    private Slot sampleSlot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Floor floor = Floor.builder().id(1L).name("Floor 1").build();
        sampleSlot = Slot.builder().id(1L).slotNumber("A1").floor(floor).build();
    }

    @Test
    void testCreateReservation_Success() {
        ReservationRequest request = new ReservationRequest();
        request.setSlotId(1L);
        request.setVehicleNumber("KA05MH1234");
        request.setStartTime(LocalDateTime.now().plusHours(1));
        request.setEndTime(LocalDateTime.now().plusHours(2));

        when(slotRepository.findById(1L)).thenReturn(Optional.of(sampleSlot));
        when(reservationRepository.findOverlappingReservations(any(), any(), any())).thenReturn(Collections.emptyList());
        when(feeCalculator.calculateFee(any(), any())).thenReturn(new BigDecimal("20"));
        when(reservationRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Reservation result = reservationService.createReservation(request);

        assertNotNull(result);
        assertEquals("KA05MH1234", result.getVehicleNumber());
        assertEquals(ReservationStatus.ACTIVE, result.getStatus());
        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    void testCreateReservation_Overlap() {
        ReservationRequest request = new ReservationRequest();
        request.setSlotId(1L);
        request.setStartTime(LocalDateTime.now());
        request.setEndTime(LocalDateTime.now().plusHours(1));

        when(slotRepository.findById(1L)).thenReturn(Optional.of(sampleSlot));
        when(reservationRepository.findOverlappingReservations(any(), any(), any()))
                .thenReturn(Collections.singletonList(new Reservation()));

        assertThrows(OverlappingReservationException.class, () -> reservationService.createReservation(request));
    }

    @Test
    void testCreateReservation_InvalidDuration() {
        ReservationRequest request = new ReservationRequest();
        request.setSlotId(1L);
        request.setStartTime(LocalDateTime.now());
        request.setEndTime(LocalDateTime.now().plusHours(25));

        when(slotRepository.findById(1L)).thenReturn(Optional.of(sampleSlot));

        assertThrows(InvalidTimeRangeException.class, () -> reservationService.createReservation(request));
    }
}
