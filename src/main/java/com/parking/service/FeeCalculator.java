package com.parking.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class FeeCalculator {
    private static final BigDecimal BASE_RATE = new BigDecimal("20");

    public BigDecimal calculateFee(LocalDateTime start, LocalDateTime end) {
        long minutes = Duration.between(start, end).toMinutes();
        if (minutes <= 0) return BigDecimal.ZERO;
        
        long hours = (long) Math.ceil(minutes / 60.0);
        return BASE_RATE.multiply(new BigDecimal(hours));
    }
}
