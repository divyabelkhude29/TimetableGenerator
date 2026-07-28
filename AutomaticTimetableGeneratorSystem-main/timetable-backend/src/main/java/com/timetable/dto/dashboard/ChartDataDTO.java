package com.timetable.dto.dashboard;

public class ChartDataDTO {

    /**
     * Chart label.
     * Example:
     * Monday
     * CSE
     * Semester 1
     * Room A101
     */
    private String label;

    /**
     * Numeric value.
     */
    private Long value;

    public ChartDataDTO() {
    }

    public ChartDataDTO(String label, Long value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

}