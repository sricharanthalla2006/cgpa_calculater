package com.example.cgpa;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GradeMapper {
    private static final Map<String, BigDecimal> map = new HashMap<>();

    static {
        // --- CUSTOMIZE THIS MAP TO MATCH YOUR UNIVERSITY'S GRADING SCALE ---
        // Example: A+ = 10.0, A = 9.0, B+ = 8.0, etc.
        map.put("A+", new BigDecimal("10.00"));
        map.put("A", new BigDecimal("9.00"));
        map.put("B+", new BigDecimal("8.00"));
        map.put("B", new BigDecimal("7.00"));
        map.put("C+", new BigDecimal("6.00"));
        map.put("C", new BigDecimal("5.00"));
        map.put("D", new BigDecimal("4.00"));
        map.put("F", new BigDecimal("0.00"));
    }

    /**
     * Returns the numeric grade point value for a given letter grade.
     * Returns BigDecimal. ZERO (0.00) if the grade is not found or is null.
     */
    public static BigDecimal pointsFor(String grade) {
        if (grade == null) return BigDecimal.ZERO;
        // Converts the input grade to uppercase and trims spaces before lookup
        return map.getOrDefault(grade.toUpperCase().trim(), BigDecimal.ZERO);
    }
}