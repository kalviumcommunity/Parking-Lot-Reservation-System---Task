package com.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SlotRequest {
    @NotNull(message = "Floor ID is required")
    private Long floorId;
    @NotBlank(message = "Slot number is required")
    private String slotNumber;
}
