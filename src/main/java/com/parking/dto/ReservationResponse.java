package com.parking.dto;

import com.parking.entity.ReservationStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReservationResponse {
    private Long id;
    private String slotNumber;
    private String floorName;
    private String vehicleNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal totalFee;
    private ReservationStatus status;
}
