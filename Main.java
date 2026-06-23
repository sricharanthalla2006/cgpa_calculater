package com.example.cgpa;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 1️⃣ Initialize Core Components
        DataSource ds = DBCPool.getDataSource();
        GradeDAO dao = new GradeDAO(ds);
        Scanner sc = new Scanner(System.in);

        System.out.println("--- CGPA Calculator Console Application ---");
        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine();

        System.out.print("Enter Current Term (e.g. Sem1): ");
        String term = sc.nextLine();

        // 2️⃣ Data Entry Loop
        System.out.println("\n--- Enter Subject Grades (Type 'done' for subject code to calculate) ---");

        while (true) {
            System.out.print("Subject Code (or 'done'): ");
            String code = sc.nextLine();
            if ("done".equalsIgnoreCase(code)) break;

            System.out.print("Subject Name: ");
            String name = sc.nextLine();

            BigDecimal credits;
            try {
                System.out.print("Credits (e.g. 3.0): ");
                credits = new BigDecimal(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid credit input. Skipping entry.");
                continue;
            }

            System.out.print("Grade (e.g. A, B+): ");
            String grade = sc.nextLine();

            BigDecimal gradePoints = GradeMapper.pointsFor(grade);
            GradeEntry entry = new GradeEntry(studentId, term, code, name, credits, grade, gradePoints);

            try {
                dao.saveGrade(entry);
                System.out.println("--> Saved entry: " + name + " (" + grade + ")");

                // ✅ NEW: Fetch latest CGPA after each entry
                List<GradeEntry> allGrades = dao.fetchGrades(studentId);
                BigDecimal currentCgpa = CGPACalculator.calculateCGPA(allGrades);

                System.out.println("📊 Updated CGPA after this subject: " + currentCgpa);
                System.out.println("---------------------------------------------");

            } catch (SQLException e) {
                System.err.println("❌ Failed to save grade to database: " + e.getMessage());
                
            }
        }

        // 3️⃣ Final CGPA display
        try {
            List<GradeEntry> allGrades = dao.fetchGrades(studentId);
            BigDecimal cgpa = CGPACalculator.calculateCGPA(allGrades);

            System.out.println("\n=============================================");
            System.out.println("✅ FINAL CGPA for Student ID " + studentId + " is: " + cgpa);
            System.out.println("Calculated over " + allGrades.size() + " total subject entries.");
            System.out.println("=============================================");

        } catch (SQLException e) {
            System.err.println("❌ Failed to fetch grades for calculation: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
