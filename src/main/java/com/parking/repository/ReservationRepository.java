package com.parking.repository;

import com.parking.entity.Reservation;
import com.parking.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.slot = :slot AND r.status = 'ACTIVE' AND r.startTime < :endTime AND r.endTime > :startTime")
    List<Reservation> findOverlappingReservations(@Param("slot") Slot slot,
                                                @Param("startTime") LocalDateTime startTime,
                                                @Param("endTime") LocalDateTime endTime);

    @Query("SELECT r.slot FROM Reservation r WHERE r.status = 'ACTIVE' AND r.startTime < :endTime AND r.endTime > :startTime")
    List<Slot> findReservedSlotsInTimeRange(@Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);
}
