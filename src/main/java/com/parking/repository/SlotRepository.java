package com.parking.repository;

import com.parking.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<Slot> findBySlotNumber(String slotNumber);
    List<Slot> findByFloorId(Long floorId);
    List<Slot> findByIsAvailableTrue();
}
