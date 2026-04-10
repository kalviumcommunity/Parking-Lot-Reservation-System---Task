package com.parking.service;

import com.parking.dto.FloorRequest;
import com.parking.entity.Floor;
import com.parking.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FloorService {
    private final FloorRepository floorRepository;

    public Floor createFloor(FloorRequest request) {
        Floor floor = Floor.builder()
                .floorNumber(request.getFloorNumber())
                .name(request.getName())
                .build();
        return floorRepository.save(floor);
    }
}
