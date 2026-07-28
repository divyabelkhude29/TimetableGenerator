package com.timetable.analytics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.timetable.dto.dashboard.ChartDataDTO;

public final class AnalyticsUtil {

    private AnalyticsUtil() {
    }

    /**
     * Calculate percentage.
     */
    public static double percentage(
            long obtained,
            long total) {

        if (total == 0) {
            return 0.0;
        }

        return round(
                ((double) obtained / total) * 100);
    }

    /**
     * Round to 2 decimal places.
     */
    public static double round(
            double value) {

        return BigDecimal
                .valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Sum chart values.
     */
    public static long totalChartValue(
            List<ChartDataDTO> chart) {

        if (chart == null) {
            return 0;
        }

        long total = 0;

        for (ChartDataDTO item : chart) {

            if (item.getValue() != null) {

                total += item.getValue();
            }
        }

        return total;
    }

    /**
     * Average chart value.
     */
    public static double averageChartValue(
            List<ChartDataDTO> chart) {

        if (chart == null || chart.isEmpty()) {
            return 0;
        }

        return round(
                (double) totalChartValue(chart)
                        / chart.size());
    }

    /**
     * Maximum value.
     */
    public static long maxChartValue(
            List<ChartDataDTO> chart) {

        long max = 0;

        if (chart == null) {
            return max;
        }

        for (ChartDataDTO item : chart) {

            if (item.getValue() != null &&
                    item.getValue() > max) {

                max = item.getValue();
            }
        }

        return max;
    }

    /**
     * Minimum value.
     */
    public static long minChartValue(
            List<ChartDataDTO> chart) {

        if (chart == null || chart.isEmpty()) {
            return 0;
        }

        long min = Long.MAX_VALUE;

        for (ChartDataDTO item : chart) {

            if (item.getValue() != null &&
                    item.getValue() < min) {

                min = item.getValue();
            }
        }

        return min == Long.MAX_VALUE ? 0 : min;
    }

}