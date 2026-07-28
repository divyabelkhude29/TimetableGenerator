package com.timetable.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class StatisticsCalculatorTest {

    private final StatisticsCalculator calculator =
            new StatisticsCalculator();

    @Test
    void testAttendancePercentage() {

        double percentage =
                calculator.calculateAttendancePercentage(
                        80,
                        100);

        assertEquals(80.0, percentage);
    }

    @Test
    void testWorkloadPercentage() {

        double percentage =
                calculator.calculateWorkloadPercentage(
                        18,
                        20);

        assertEquals(90.0, percentage);
    }

    @Test
    void testAverageAttendance() {

        double average =
                calculator.calculateAverageAttendance(
                        Arrays.asList(
                                80.0,
                                90.0,
                                100.0));

        assertEquals(90.0, average);
    }

}