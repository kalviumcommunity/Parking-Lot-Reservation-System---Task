package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OverlappingReservationException extends RuntimeException {
    public OverlappingReservationException(String message) {
        super(message);
    }
}
