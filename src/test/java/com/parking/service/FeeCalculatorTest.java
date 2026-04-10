package com.parking.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FeeCalculatorTest {
    private final FeeCalculator calculator = new FeeCalculator();

    @Test
    void testCalculateFee_ExactHour() {
        LocalDateTime start = LocalDateTime.of(2024, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 4, 1, 11, 0);
        assertEquals(new BigDecimal("20"), calculator.calculateFee(start, end));
    }

    @Test
    void testCalculateFee_PartialHour() {
        LocalDateTime start = LocalDateTime.of(2024, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 4, 1, 10, 30);
        assertEquals(new BigDecimal("20"), calculator.calculateFee(start, end));
    }

    @Test
    void testCalculateFee_OverOneHour() {
        LocalDateTime start = LocalDateTime.of(2024, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 4, 1, 11, 1);
        assertEquals(new BigDecimal("40"), calculator.calculateFee(start, end));
    }

    @Test
    void testCalculateFee_ZeroDuration() {
        LocalDateTime now = LocalDateTime.now();
        assertEquals(BigDecimal.ZERO, calculator.calculateFee(now, now));
    }
}
