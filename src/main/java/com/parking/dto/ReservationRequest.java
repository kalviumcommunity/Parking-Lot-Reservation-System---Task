package com.parking.dto;

import com.parking.validation.ValidVehicleNumber;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationRequest {
    @NotNull(message = "Slot ID is required")
    private Long slotId;

    @ValidVehicleNumber
    private String vehicleNumber;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
}
