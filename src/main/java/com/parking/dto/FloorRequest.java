package com.parking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FloorRequest {
    private int floorNumber;
    @NotBlank(message = "Floor name is required")
    private String name;
}
