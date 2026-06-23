package com.example.cgpa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CGPACalculator {

    /**
     * Calculates the CGPA using the formula: Sum(GradePoints * Credits) / Sum(Credits)
     * * @param entries A list of GradeEntry objects (subjects taken by the student).
     * @return The calculated CGPA as a BigDecimal, rounded to 2 decimal places.
     */
    public static BigDecimal calculateCGPA(List<GradeEntry> entries) {
        BigDecimal totalCredits = BigDecimal.ZERO;
        BigDecimal weightedSum = BigDecimal.ZERO; // Represents Sum(GradePoints * Credits)

        for (GradeEntry e : entries) {
            // Ensure inputs are valid before processing
            if (e.getCredits() == null || e.getGradePoints() == null) continue;

            // 1. Accumulate total credits
            totalCredits = totalCredits.add(e.getCredits());

            // 2. Calculate and accumulate the weighted sum (Points * Credits)
            BigDecimal subjectWeightedPoints = e.getGradePoints().multiply(e.getCredits());
            weightedSum = weightedSum.add(subjectWeightedPoints);
        }

        // Check to prevent division by zero
        if (totalCredits.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        // 3. CGPA = Weighted Sum / Total Credits
        // Divide and round the result to 2 decimal places using standard rounding (HALF_UP)
        BigDecimal cgpa = weightedSum.divide(totalCredits, 2, RoundingMode.HALF_UP);
        return cgpa;
    }
}